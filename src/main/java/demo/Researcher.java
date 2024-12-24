package demo;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;
@Entity
public class Researcher extends Person {
    @OneToMany
    private List<Project> participatingProjects;
    @OneToMany
    private List<Project> addedProjects;

    protected Researcher(){
        super();
    }
    public Researcher(String firstName, String lastName, String username, String password) {
        super(username, firstName, lastName, password);
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
