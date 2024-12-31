package demo.model;

import jakarta.persistence.*;

@Entity
public class PendingProject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "researcher_id")
    private Researcher researcher;

    private boolean accepted;

    protected PendingProject() {}

    public PendingProject(Project project, Manager manager, Researcher researcher) {
        this.project = project;
        this.manager = manager;
        this.researcher = researcher;
        this.accepted = false; // Stato iniziale
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
