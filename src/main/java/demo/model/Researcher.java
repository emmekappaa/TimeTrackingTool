package demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;
@Entity
public class Researcher extends Person {
    @ManyToMany
    private List<Project> participatingProjects;
    @ManyToMany
    private List<Project> addedProjects;

    protected Researcher(){
        super();
    }
    public Researcher(String firstName, String lastName, String username, String password,String cf) {
        super(username, firstName, lastName, password, cf);
    }

    public List<Project> getParticipatingProjects() {
        return participatingProjects;
    }

    public void setParticipatingProjects(List<Project> participatingProjects) {
        this.participatingProjects = participatingProjects;
    }

    public List<Project> getAddedProjects() {
        return addedProjects;
    }

    public void setAddedProjects(List<Project> addedProjects) {
        this.addedProjects = addedProjects;
    }
}
