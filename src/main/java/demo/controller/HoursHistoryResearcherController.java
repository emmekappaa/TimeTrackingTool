package demo.controller;

import demo.model.Person;
import demo.model.Project;
import demo.model.TimeLog;
import demo.repository.ProjectRepository;
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


import java.util.ArrayList;

@Controller
@RequestMapping("/hoursHistoryResearcher")
public class HoursHistoryResearcherController {
    @Autowired
    private TimeLogRepository timeLogRepository;

    @RequestMapping("")
    public String hoursHistory(HttpSession session, HttpServletResponse response, Model model){
        System.out.println("hoursHistoryResearcherController");
        // disabilito cache, non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control","no-store");

        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        String role = (String) session.getAttribute("role");
        if (loggedInUser == null || !"Researcher".equals(role)) {
            session.invalidate();
            return "redirect:/";
        }

        model.addAttribute("username", loggedInUser.getFirstName() + " " + loggedInUser.getLastName());

        ArrayList<TimeLog> timeLogs = new ArrayList<>();
        for (TimeLog t : timeLogRepository.findAllByPerson(loggedInUser)) {
            timeLogs.add(t);
        }
        model.addAttribute("timeLogs", timeLogs);

        return "hoursHistoryResearcher";
    }

    /*
    @RequestMapping("/homeResearcher")
    public String backHome() {
        return "homeResearcher";
    }
    */

}
