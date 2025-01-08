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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
    public String back(HttpSession session) {

        boolean role = session.getAttribute("role").toString().equals("Manager");
        if (role) {
            return "redirect:/homeManager";
        }else {
            return "redirect:/homeResearcher";
        }

    }
    @PostMapping("/sign")
    public String sign(@RequestParam(value = "selectedProject", required = false) String selectedProject,@RequestParam(value = "month", required = false) Integer month, @RequestParam(value = "year", required = false) Integer year, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        System.out.println("SING INVOKED" + selectedProject + month + year);
        LocalDate now = LocalDate.now();
        month = (month != null) ? month : now.getMonthValue();
        year = (year != null) ? year : now.getYear();
        boolean role = session.getAttribute("role").toString().equals("Manager");
        Project project = null;

        Person selected = (Person) session.getAttribute("selectedUser");

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

        if(role){
            //manager firma ore ricercatore
            if(selected instanceof Researcher)
            {
                Signature tmp = sr.findByPersonAndProjectAndMonthrAndYearrAndManager(selected,project,month,year,(Manager)loggedInUser).orElse(null);
                if(tmp != null){
                    tmp.setSignM();
                    //DEVO SALVARE NUOVAMENTE OGGETTO IN COPIA NON REFERENZA
                    sr.save(tmp);
                }
                else
                {
                    Signature news = new Signature(selected, (Manager) loggedInUser,project,month,year);
                    news.setSignM();
                    sr.save(news);
                }

            }
            else //altrimenti manager che si firma le sue ore
            {
                Signature tmp = sr.findByPersonAndProjectAndMonthrAndYearrAndManager(loggedInUser,project, month,year,(Manager) loggedInUser).orElse(null);
                if(tmp != null){
                    tmp.setSignM();
                    sr.save(tmp);
                }
                else
                {
                    Signature news = new Signature(loggedInUser, (Manager) loggedInUser,project,month,year);
                    news.setSignM();
                    sr.save(news);
                }
            }

        }
        else //sono un ricercatore
        {
            assert project != null;
            Signature tmp = sr.findByPersonAndProjectAndMonthrAndYearrAndManager(loggedInUser,project, month,year,project.getManager()).orElse(null);
            if(tmp != null){
                tmp.setSignR();
                sr.save(tmp);
            }
            else
            {
                Signature news = new Signature(loggedInUser,project.getManager(),project,month,year);
                news.setSignR();
                sr.save(news);
            }
        }


        System.out.println("salvato");

        assert project != null;
        return "redirect:/monthly/report?selectedProject=" + URLEncoder.encode(project.getName(), StandardCharsets.UTF_8) + "&month=" + month + "&year=" + year;


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
        boolean queryAux = false;
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

        ArrayList<Project> allProjects;

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

        ArrayList<Project> otherProjectsWithSameOrganization;

        assert project != null;
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

        if(!role)// se sono un ricercatore
        {
            Signature tmp = sr.findByPersonAndProjectAndMonthrAndYearrAndManager(loggedInUser, project, selectedMonth, selectedYear,project.getManager()).orElse(null);
            if(tmp != null) {
                if (tmp.getSignR())
                    model.addAttribute("researcherRS", loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                if (tmp.getSignM())
                    model.addAttribute("researcherMS", project.getManager().getFirstName() + " " + project.getManager().getLastName());
            }

        }
        else // se sono un manager
        {
            if (!queryAux){ // e il selezionato e' ricercatore
                Signature tmp = sr.findByPersonAndProjectAndMonthrAndYearrAndManager(loggedInUser, project, selectedMonth, selectedYear,project.getManager()).orElse(null);
                if(tmp != null){
                    if(tmp.getSignR())
                        model.addAttribute("researcherRS",loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
                    if(tmp.getSignM())
                        model.addAttribute("researcherMS",project.getManager().getFirstName() + " " + project.getManager().getLastName());
                }

            }
            else // se il selezionato sono io (manager)
            {
                Signature tmp = sr.findByPersonAndProjectAndMonthrAndYearrAndManager((Person) session.getAttribute("loggedInUser"),project, selectedMonth, selectedYear, (Manager) session.getAttribute("loggedInUser")).orElse(null);
                if(tmp != null){
                    if(tmp.getSignM()){
                        model.addAttribute("researcherMS",project.getManager().getFirstName() + " " + project.getManager().getLastName());
                    }
                }
            }
        }


        return "report";
    }


    record DayData(int day, double projectHours, double otherProjectsHours, double otherProjectsHoursSameOrganization,
                   double totalHours) {
    }

}
