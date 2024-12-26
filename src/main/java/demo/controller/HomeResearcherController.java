package demo.controller;

import demo.model.Person;
import demo.model.Project;
import demo.model.Researcher;
import demo.repository.ProjectRepository;
import demo.repository.Repository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/homeResearcher")
public class HomeResearcherController {
    @Autowired
    private ProjectRepository pr;
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
        ArrayList<Project> researcherProjects = new ArrayList<>();
        for(Project p : pr.findAll()){
            if(p.getResearchers().contains(loggedInUser)){
                researcherProjects.add(p);
                System.out.println(p.getName());
            }
        }
        model.addAttribute("researcherProjects", researcherProjects);

        return "homeResearcher";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
