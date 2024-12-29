package demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;
@Entity
public class Manager extends Person {

    //Un Manager può gestire molti progetti.
    //Ogni progetto è assegnato a un solo manager.
    @OneToMany
    private List<Project> assignedProjects;

    protected Manager(){
        super();
    }

    public Manager(String firstName, String lastName, String username, String password, String cf) {
        super(username, firstName, lastName, password, cf);
    }

    public List<Project> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(List<Project> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }
}
