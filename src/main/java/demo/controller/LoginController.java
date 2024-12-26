package demo.controller;

import demo.model.Researcher;
import demo.repository.Repository;
import demo.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Person person = repository.findByUsername(username);
        System.out.println(username);
        System.out.println(password);
        System.out.println(person.getFirstName());

        if(person.getPassword().equals(password)){

            session.setAttribute("loggedInUser",person);

            if(person instanceof Researcher)
            {
                //chiamo controller home Researcher
                session.setAttribute("role", "Researcher");
                return "redirect:/homeResearcher";
            }
            else
            {
                //chiamo controller home Manager
                session.setAttribute("role", "Manager");
                return "redirect:/homeManager";
            }


        }
        return "login";
    }
}