package demo.controller;

import demo.model.Person;
import demo.model.TimeLog;
import demo.repository.TimeLogRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;

@Controller
@RequestMapping("/hoursHistory")
public class HoursHistoryController {
    @Autowired
    private TimeLogRepository timeLogRepository;
    @PostMapping("/back")
    public String back(HttpSession session) {

        boolean role = session.getAttribute("role").toString().equals("Manager");
        if (role) {
            return "redirect:/homeManager";
        }else {
            return "redirect:/homeResearcher";
        }

    }
    @RequestMapping("")
    public String hoursHistory(HttpSession session, HttpServletResponse response, Model model){
        // disabilito cache, non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control","no-store");

        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            session.invalidate();
            return "redirect:/";
        }

        model.addAttribute("username", loggedInUser.getFirstName() + " " + loggedInUser.getLastName());

        ArrayList<TimeLog> timeLogs = new ArrayList<>(timeLogRepository.findAllByPerson(loggedInUser));
        model.addAttribute("timeLogs", timeLogs);

        return "hoursHistory";
    }

}
