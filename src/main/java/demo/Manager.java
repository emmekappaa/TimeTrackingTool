package demo;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;
@Entity
public class Manager extends Person {
    @OneToMany
    private List<Project> assignedProjects;

    protected Manager(){
        super();
    }

    public Manager(String firstName, String lastName, String username, String password) {
        super(username, firstName, lastName, password);
    }

    public List<Project> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(List<Project> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }
}
