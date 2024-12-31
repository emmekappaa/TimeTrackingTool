package demo.controller;

import demo.model.Person;
import demo.model.Project;
import demo.model.Researcher;
import demo.model.TimeLog;
import demo.repository.ProjectRepository;
import demo.repository.Repository;
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
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/homeManager")
public class HomeManagerController {

    @Autowired
    private ProjectRepository pr;

    @Autowired
    private TimeLogRepository timeLogRepository;
    @Autowired
    private Repository repo;

    @RequestMapping("")
    public String homePage(HttpSession session, HttpServletResponse response, Model model) {

        // disabilito cache non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control","no-store");

        //System.out.println("Sono in esecuzione");
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        String role = (String) session.getAttribute("role");
        if (loggedInUser == null || !"Manager".equals(role)) {
            session.invalidate();
            return "redirect:/";
        }
        model.addAttribute("username", loggedInUser.getFirstName() + " " + loggedInUser.getLastName());

        // Ottieni la data odierna
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        model.addAttribute("todayDate", formattedDate);

        // retrivo progetti a cui partecipa e li addo al model
        ArrayList<Project> managerProjects = new ArrayList<>();
        for(Project p : pr.findAll()){
            if(p.getManager().equals(loggedInUser)){
                managerProjects.add(p);
                System.out.println(p.getName());
            }
        }
        model.addAttribute("managerProjects", managerProjects);

        ArrayList<Person> researchers = new ArrayList<>();

        for (Project p : managerProjects){
            researchers.addAll(p.getResearchers());
        }
        Set<Person> set  = new HashSet<>(researchers);
        researchers = new ArrayList<>(set);
        researchers.add(loggedInUser);
        Person aux = researchers.get(0);
        researchers.set(0, researchers.get(researchers.size()-1));
        researchers.set(researchers.size()-1, aux);
        model.addAttribute("researchers", researchers);


        ArrayList<TimeLog> timeLogsToday = new ArrayList<>();
        for (TimeLog t : timeLogRepository.findAllByPersonAndDate(loggedInUser, today)) {
            timeLogsToday.add(t);
        }
        model.addAttribute("timeLogsToday", timeLogsToday);

        return "homeManager";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/monthly/report")
    public String monthlyReport(@RequestParam("researcherId") long researcherId, HttpSession session, Model model) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        // Recupera il ricercatore selezionato

        Person researcher = (Person) repo.findById(researcherId).orElse(null);
        if (researcher == null) {
            model.addAttribute("error", "Researcher not found.");
            return "homeManager";
        }


        System.out.println(researcher.getFirstName() + " " + researcher.getLastName() + "PRIMA");
        session.setAttribute("selectedUser", researcher);
        //model.addAttribute("selectedUser", researcher);

        // Logica per il report mensile basata sul ricercatore
        return "redirect:/monthly/report";
    }



    @PostMapping("/addTimeLog")
    public String addTimeLog(@RequestParam("hoursWorked") double hoursWorked, @RequestParam("projectId") long projectId, HttpSession session, Model model, RedirectAttributes redirectAttributes){
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
                ArrayList<TimeLog> timeLogsToday = new ArrayList<>();
                for (TimeLog t : timeLogRepository.findAllByPersonAndDate(loggedInUser, today)) {
                    timeLogsToday.add(t);
                }
                redirectAttributes.addFlashAttribute("timeLogsToday", timeLogsToday);
                return "redirect:/homeManager";
            }
            else
            {

                TimeLog timeLog = new TimeLog(loggedInUser, project, today, hoursWorked);
                timeLogRepository.save(timeLog);
                System.out.println("Aggiunte " + timeLog.getHoursWorked() + " ore al progetto " + project.getName());
            }
        }

        LocalDate today = LocalDate.now();
        ArrayList<TimeLog> timeLogsToday = new ArrayList<>();
        for (TimeLog t : timeLogRepository.findAllByPersonAndDate(loggedInUser, today)) {
            timeLogsToday.add(t);
        }
        redirectAttributes.addFlashAttribute("timeLogsToday", timeLogsToday);
        return "redirect:/homeManager";

    }

}
