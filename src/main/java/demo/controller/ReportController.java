package demo.controller;

import demo.model.Person;
import demo.model.Project;
import demo.model.Researcher;
import demo.model.TimeLog;
import demo.repository.ProjectRepository;
import demo.repository.TimeLogRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("")
    public String showReport(HttpSession session, HttpServletResponse response, Model model, @RequestParam(value = "selectedProject", required = false) String selectedProject, @RequestParam(value = "month", required = false) Integer month, @RequestParam(value = "year", required = false) Integer year) {
        // disabilito cache, non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control", "no-store");
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/";
        }
        System.out.println(selectedProject + " " + month + " " + year);
        Project project = projectRepository.findByName(selectedProject);
        if (project != null) {
            System.out.println(project.getName() + "test");
        }

        if (project == null){
            for (Project p : projectRepository.findAll()) {
                if (p.getResearchers().contains(loggedInUser)) {
                    project = p;
                    break;
                }
            }
        }

        ArrayList<Project> allProjects = new ArrayList<>();
        allProjects = projectRepository.findByResearchersContains((Researcher) loggedInUser);
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
        otherProjectsWithSameOrganization = projectRepository.findByResearchersContainsAndOrganizationName((Researcher) loggedInUser, project.getOrganizationName());

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
                        System.out.println("CI ENTRO");
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
