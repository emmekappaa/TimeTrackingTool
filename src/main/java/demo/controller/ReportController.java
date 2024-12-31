package demo.controller;

import demo.model.*;
import demo.repository.ProjectRepository;
import demo.repository.SignatureRepository;
import demo.repository.TimeLogRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/monthly/report")
public class ReportController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TimeLogRepository tml;

    @Autowired
    private SignatureRepository sr;

    @PostMapping("/back")
    public String back(HttpSession session, Model model) {

        Boolean role = session.getAttribute("role").toString().equals("Manager");
        if (role) {
            return "redirect:/homeManager";
        }else {
            return "redirect:/homeResearcher";
        }

    }
    @PostMapping("/sign")
    public String sign(@RequestParam(value = "selectedProject", required = false) String selectedProject,@RequestParam(value = "month", required = false) Integer month, @RequestParam(value = "year", required = false) Integer year, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        LocalDate now = LocalDate.now();
        month = (month != null) ? month : now.getMonthValue();
        year = (year != null) ? year : now.getYear();
        Boolean role = session.getAttribute("role").toString().equals("Manager");
        Project project = null;

        System.out.println("VUOLE FIRMARE "  + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());

        if (selectedProject == null){
            for (Project p : projectRepository.findAll()) {


                if (!role && p.getResearchers().contains(loggedInUser)) {
                    project = p;
                    break;
                }

                if (role && p.getManager().equals(loggedInUser)) {
                    project = p;
                    break;
                }
            }
        }
        else{
            project = projectRepository.findByName(selectedProject);
        }

        sr.save(new Signature(loggedInUser,project,month,year));

        System.out.println("salvato");
        return "redirect:/monthly/report";
    }

    @RequestMapping("")
    public String showReport(HttpSession session, HttpServletResponse response, Model model, @RequestParam(value = "selectedProject", required = false) String selectedProject, @RequestParam(value = "month", required = false) Integer month, @RequestParam(value = "year", required = false) Integer year) {
        // disabilito cache, non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control", "no-store");
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/";
        }

        System.out.println("PROVA");
        Boolean role = session.getAttribute("role").toString().equals("Manager");
        Boolean queryAux = false;
        System.out.println(role);
        if (role){
            loggedInUser = (Person) session.getAttribute("selectedUser");
            System.out.println(loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "DOPO");
            queryAux = loggedInUser instanceof Manager;

        }

        System.out.println(role);
        model.addAttribute("manager", role);
        System.out.println(selectedProject + " " + month + " " + year);
        Project project = projectRepository.findByName(selectedProject);
        if (project != null) {
            System.out.println(project.getName() + "test");
        }

        if (project == null){
            for (Project p : projectRepository.findAll()) {

                if (role && queryAux && p.getManager().equals(loggedInUser)){
                    project = p;
                    break;
                }

                if (role && !queryAux && p.getResearchers().contains(loggedInUser)){
                    project = p;
                    break;
                }

                if (p.getResearchers().contains(loggedInUser)){
                    project = p;
                    break;
                }

            }
        }

        ArrayList<Project> allProjects = new ArrayList<>();

        if (role){

            if (queryAux){
                allProjects = projectRepository.findByManager((Manager)loggedInUser);
            }else{
                allProjects = projectRepository.findByResearchersContains((Researcher) loggedInUser);
            }


        }else{
            allProjects = projectRepository.findByResearchersContains((Researcher) loggedInUser);
        }


        model.addAttribute("projects", allProjects);
        ArrayList<Project> projectsBySameOrganization = new ArrayList<>();
        model.addAttribute("selectedProject", project);

        if (project != null) {
            model.addAttribute("projectTitle", project.getName());
            model.addAttribute("cup", project.getCup());
            model.addAttribute("projectCode", project.getProjectCode());
            model.addAttribute("organizationName", project.getOrganizationName());
        }

        model.addAttribute("researcherName", loggedInUser.getFirstName());
        model.addAttribute("researcherSurname", loggedInUser.getLastName());
        model.addAttribute("fiscalCode", loggedInUser.getCf());
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);


        // Determina il mese e l'anno selezionati altrimenti pulla quelli di oggi
        LocalDate now = LocalDate.now();
        int selectedMonth = (month != null) ? month : now.getMonthValue();
        int selectedYear = (year != null) ? year : now.getYear();

        LocalDate startOfMonth = LocalDate.of(selectedYear, selectedMonth, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<TimeLog> timeLogs = new ArrayList<>();

        for (TimeLog t : tml.findAllByPerson(loggedInUser)) {
            if (!t.getDate().isBefore(startOfMonth) && !t.getDate().isAfter(endOfMonth)) {
                timeLogs.add(t);
            }
        }

        ArrayList<Project> otherProjectsWithSameOrganization = new ArrayList<>();

        if (role){
            if (queryAux){
                otherProjectsWithSameOrganization = projectRepository.findByManagerAndOrganizationName((Manager)loggedInUser, project.getOrganizationName());
            }else{
                otherProjectsWithSameOrganization = projectRepository.findByResearchersContainsAndOrganizationName((Researcher) loggedInUser, project.getOrganizationName());
            }

        }else {
            otherProjectsWithSameOrganization = projectRepository.findByResearchersContainsAndOrganizationName((Researcher) loggedInUser, project.getOrganizationName());
        }


        List<DayData> days = new ArrayList<>();
        for (int day = 1; day <= startOfMonth.lengthOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(selectedYear, selectedMonth, day);

            double projectHours = 0;
            double otherProjectsHours = 0;
            double otherProjectsHoursSameOrganization = 0;

            for (TimeLog log : timeLogs) {
                if (log.getDate().equals(currentDate)) {
                    if (log.getProject().equals(project)) {

                        projectHours += log.getHoursWorked();
                    } else {
                        if (otherProjectsWithSameOrganization.contains(log.getProject())){
                            otherProjectsHoursSameOrganization += log.getHoursWorked();
                        }

                        otherProjectsHours += log.getHoursWorked();
                    }
                }
            }

            days.add(new DayData(day, projectHours, otherProjectsHours, otherProjectsHoursSameOrganization, projectHours + otherProjectsHours));
        }

        model.addAttribute("days", days);
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("selectedYear", selectedYear);

        if(!role)
        {
            if(sr.findByPersonAndProjectAndMonthrAndYearr(loggedInUser, project, selectedMonth, selectedYear).isPresent())
                model.addAttribute("researcherRS",loggedInUser.getFirstName() + " " + loggedInUser.getLastName());

            if(sr.findByPersonAndProjectAndMonthrAndYearr(project.getManager(), project, selectedMonth, selectedYear).isPresent())
                model.addAttribute("researcherMS",project.getManager().getFirstName() + " " + project.getManager().getLastName());
        }
        else
        {

            if (!queryAux){
                if(sr.findByPersonAndProjectAndMonthrAndYearr(loggedInUser, project, selectedMonth, selectedYear).isPresent())
                    model.addAttribute("researcherRS",loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
            }else{

            }

            System.out.println("MANAGER" + project.getManager().getFirstName() + " " + project.getManager().getLastName());

            if(sr.findByPersonAndProjectAndMonthrAndYearr((Person) session.getAttribute("loggedInUser"), project, selectedMonth, selectedYear).isPresent())
                model.addAttribute("researcherMS",project.getManager().getFirstName() + " " + project.getManager().getLastName());
        }


        return "report";
    }



    static class DayData {
        private int day;
        private double projectHours;
        private double otherProjectsHours;
        private double otherProjectsHoursSameOrganization;
        private double totalHours;

        public DayData(int day, double projectHours, double otherProjectsHours, double otherProjectsHoursSameOrganization, double totalHours) {
            this.day = day;
            this.projectHours = projectHours;
            this.otherProjectsHours = otherProjectsHours;
            this.otherProjectsHoursSameOrganization = otherProjectsHoursSameOrganization;
            this.totalHours = totalHours;
        }

        // Getters
        public int getDay() { return day; }
        public double getProjectHours() { return projectHours; }
        public double getOtherProjectsHours() { return otherProjectsHours; }
        public double getTotalHours() { return totalHours; }
        public double getOtherProjectsHoursSameOrganization() { return otherProjectsHoursSameOrganization; }
    }

}
