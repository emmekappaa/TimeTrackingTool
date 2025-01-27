package demo.controller;

import demo.model.PendingProject;
import demo.model.Person;
import demo.model.Project;
import demo.model.Researcher;
import demo.repository.PendingProjectRepository;
import demo.repository.ProjectRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projectsResearcher")
public class ResearcherProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PendingProjectRepository pendingProjectrepo;

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

        List<PendingProject> pendingProjects = pendingProjectrepo.findByResearcher((Researcher) loggedInUser);

        model.addAttribute("activeProjects", activeProjects);
        model.addAttribute("pendingProjects", pendingProjects);

        return "projectsResearcher";
    }

    // Metodo per gestire l'accettazione del progetto
    @RequestMapping("/accept")
    public String acceptProject(@RequestParam("projectId") Long projectId, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            PendingProject project = pendingProjectrepo.findById(projectId).orElse(null);
            if (project != null) {
                Project toAdd = project.getProject();
                ArrayList<Researcher> r = new ArrayList<>(toAdd.getResearchers());
                r.add((Researcher) loggedInUser);
                toAdd.setResearchers(r);
                projectRepository.save(toAdd);

                // Rimuovi il pending project
                pendingProjectrepo.findById(projectId).ifPresent(pendingProject -> pendingProjectrepo.delete(pendingProject));
            }
        }
        return "redirect:/projectsResearcher";
    }

    // Metodo per gestire il rifiuto del progetto
    @RequestMapping("/decline")
    public String declineProject(@RequestParam("projectId") Long projectId) {
        pendingProjectrepo.findById(projectId).ifPresent(pendingProject -> pendingProjectrepo.delete(pendingProject));
        return "redirect:/projectsResearcher";
    }

}
