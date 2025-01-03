package demo.controller;

import demo.model.*;
import demo.repository.PendingProjectRepository;
import demo.repository.ProjectRepository;
import demo.repository.Repository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projectsManager")
public class ManagerProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private Repository personRepository;

    @Autowired
    private PendingProjectRepository pendingrepo;

    @RequestMapping("")
    public String viewProjects(HttpSession session, HttpServletResponse response, Model model) {

        // Disabilita la cache per evitare che la pagina rimanga in memoria
        response.setHeader("Cache-Control", "no-store");

        // Recupera l'utente loggato
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/";
        }

        // Recupera i progetti gestiti dal manager
        List<Project> managedProjects = projectRepository.findByManager((Manager) loggedInUser);

        // Recupera tutti i ricercatori dal repository
        List<Researcher> researchers = new ArrayList<>();
        for (Person person : personRepository.findAll()) {
            if (person instanceof Researcher) {
                researchers.add((Researcher) person);
            }
        }

        model.addAttribute("managedProjects", managedProjects);
        model.addAttribute("researchers", researchers);
        return "projectsManager";
    }

    @PostMapping("/add")
    public String addProject(
            @RequestParam String name,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String cup,
            @RequestParam String projectCode,
            @RequestParam String organizationName,
            @RequestParam List<Long> researcherIds,
            HttpSession session) {

        Manager loggedInManager = (Manager) session.getAttribute("loggedInUser");

        if (loggedInManager == null) {
            return "redirect:/";
        }

        List<Researcher> researchers = new ArrayList<>();
        for (Long id : researcherIds) {
            Researcher researcher = (Researcher) personRepository.findById(id).orElse(null);
            if (researcher != null) {
                researchers.add(researcher);
            }
        }

        Project project = new Project(
                name,
                startDate,
                endDate,
                loggedInManager,
                new ArrayList<>(),
                projectCode,
                organizationName,
                cup
        );


        projectRepository.save(project);


        for (Researcher researcher : researchers) {
            PendingProject pendingProject = new PendingProject(project, loggedInManager, researcher);
            pendingrepo.save(pendingProject);
        }

        return "redirect:/projectsManager";
    }

}
