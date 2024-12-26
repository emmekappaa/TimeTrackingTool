package demo.controller;

import demo.model.Person;
import demo.model.Researcher;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homeResearcher")
public class HomeResearcherController {

    @RequestMapping("")
    public String homePage(HttpSession session, HttpServletResponse response, Model model) {

        // disabilito cache non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control","no-store");

        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        String role = (String) session.getAttribute("role");
        if (loggedInUser == null || !"Researcher".equals(role)) {
            session.invalidate();
            return "redirect:/";
        }
        model.addAttribute("username", loggedInUser.getFirstName());

        System.out.println("loggato" + loggedInUser.getFirstName());
        return "homeResearcher";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
