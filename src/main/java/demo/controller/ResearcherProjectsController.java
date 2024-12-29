package demo.controller;

import demo.model.Person;
import demo.model.Project;
import demo.repository.ProjectRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/projectsResearcher")
public class ResearcherProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping("")
    public String viewProjects(HttpSession session, HttpServletResponse response, Model model) {

        // disabilito cache, non voglio che la pagina rimani in memoria al browser
        response.setHeader("Cache-Control","no-store");

        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/";
        }

        List<Project> activeProjects = new ArrayList<>();

        for (Project project : projectRepository.findAll()) {
            if (project.getResearchers().contains(loggedInUser)) {

                activeProjects.add(project);

            }
        }

        // Progetti in sospeso hardcoded
        List<HashMap<String, String>> pendingProjects = new ArrayList<>();
        HashMap<String, String> pendingProject1 = new HashMap<>();
        pendingProject1.put("name", "Project Gamma");
        pendingProjects.add(pendingProject1);

        HashMap<String, String> pendingProject2 = new HashMap<>();
        pendingProject2.put("name", "Project Delta");
        pendingProjects.add(pendingProject2);

        model.addAttribute("activeProjects", activeProjects);
        model.addAttribute("pendingProjects", pendingProjects);

        return "projectsResearcher";
    }

    /*
    @RequestMapping("/homeResearcher")
    public String backHome() {
        System.out.println("");
        return "homeResearcher";
    }
    */

}
