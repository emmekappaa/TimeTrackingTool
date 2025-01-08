package demo.controller;

import demo.model.Person;
import demo.model.Project;
import demo.model.Signature;
import demo.model.TimeLog;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Controller
@RequestMapping("/homeResearcher")
public class HomeResearcherController {
    @Autowired
    private ProjectRepository pr;

    @Autowired
    private TimeLogRepository timeLogRepository;

    @Autowired
    private SignatureRepository sr;

    @RequestMapping("")
    public String homePage(HttpSession session, HttpServletResponse response, Model model) {


        // disabilito cache, non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control","no-store");

        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        String role = (String) session.getAttribute("role");
        if (loggedInUser == null || !"Researcher".equals(role)) {
            session.invalidate();
            return "redirect:/";
        }

        model.addAttribute("username", loggedInUser.getFirstName() + " " + loggedInUser.getLastName());

        // Ottieni la data odierna
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        model.addAttribute("todayDate", formattedDate);

        //System.out.println("loggato" + loggedInUser.getFirstName());

        // retrivo progetti a cui partecipa e li addo al model
        ArrayList<Project> researcherProjects = new ArrayList<>();
        for(Project p : pr.findAll()){
            if(p.getResearchers().contains(loggedInUser)){
                researcherProjects.add(p);
                System.out.println(p.getName());
            }
        }
        model.addAttribute("researcherProjects", researcherProjects);

        ArrayList<TimeLog> timeLogsToday = new ArrayList<>(timeLogRepository.findAllByPersonAndDate(loggedInUser, today));
        model.addAttribute("timeLogsToday", timeLogsToday);

        return "homeResearcher";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /* inutile viene gia chiamata la pagina con il suo controllore
    @RequestMapping("/hoursHistoryResearcher")
    public String hoursHistory() {
        System.out.println("QUI QUI");
        return "/hoursHistoryResearcher";
    }
    */

    @PostMapping("/addTimeLog")
    public String addTimeLog(@RequestParam("hoursWorked") double hoursWorked, @RequestParam("projectId") long projectId, HttpSession session, RedirectAttributes redirectAttributes) {

        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        Project project = pr.findById(projectId).orElse(null);

        if (project != null && loggedInUser != null) {

            LocalDate today = LocalDate.now();

            double totalHoursWorkedToday = 0;

            for(TimeLog t : timeLogRepository.findAllByPersonAndDate(loggedInUser, today))
            {
                totalHoursWorkedToday+= t.getHoursWorked();
            }

            if (totalHoursWorkedToday + hoursWorked > 8) {

                redirectAttributes.addFlashAttribute("error", "You cannot log more than 8 working hours in a day.");
                ArrayList<TimeLog> timeLogsToday = new ArrayList<>(timeLogRepository.findAllByPersonAndDate(loggedInUser, today));
                redirectAttributes.addFlashAttribute("timeLogsToday", timeLogsToday);
                return "redirect:/homeResearcher";
            }
            else
            {
                Signature s = sr.findByPersonAndProjectAndMonthrAndYearrAndManager(loggedInUser,project,today.getMonthValue(),today.getYear(),project.getManager()).orElse(null);
                if(s == null){
                    TimeLog timeLog = new TimeLog(loggedInUser, project, today, hoursWorked);
                    timeLogRepository.save(timeLog);
                    System.out.println("Aggiunte " + timeLog.getHoursWorked() + " ore al progetto " + project.getName());
                }
                else{
                    redirectAttributes.addFlashAttribute("error", "You cannot log hours on an already signed project.");
                }

            }
        }

        LocalDate today = LocalDate.now();
        ArrayList<TimeLog> timeLogsToday = new ArrayList<>(timeLogRepository.findAllByPersonAndDate(loggedInUser, today));
        redirectAttributes.addFlashAttribute("timeLogsToday", timeLogsToday);
        return "redirect:/homeResearcher";
    }
}
