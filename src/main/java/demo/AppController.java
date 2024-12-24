package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;


@Controller
public class AppController {

    @Autowired
    private Repository repository;
    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {

        Person person = repository.findByUsername(username);
        System.out.println(username);
        System.out.println(password);
        System.out.println(person.getFirstName());

        if(person.getPassword().equals(password)){

            return "home";
        }
        return "login";
    }
}