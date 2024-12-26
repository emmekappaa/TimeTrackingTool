package demo.controller;

import demo.model.Person;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homeManager")
public class HomeManagerController {

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
        model.addAttribute("username", loggedInUser.getFirstName());

        return "homeManager";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
