package demo.controller;

import demo.model.Researcher;
import demo.repository.Repository;
import demo.model.Person;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    private Repository repository;
    @RequestMapping("/")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {

        Person person = repository.findByUsername(username);

        if (person == null || !person.getPassword().equals(password)) {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "login";
        }

        session.setAttribute("loggedInUser", person);

        if (person instanceof Researcher) {
            session.setAttribute("role", "Researcher");
            return "redirect:/homeResearcher";
        } else {
            session.setAttribute("role", "Manager");
            return "redirect:/homeManager";
        }

    }
}