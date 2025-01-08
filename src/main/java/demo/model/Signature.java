package demo.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Manager manager;

    private int monthr;

    private int yearr;

    private boolean signM;

    private boolean signR;

    public Signature() {}

    public Signature(Person person, Manager manager, Project project, int monthr, int yearr) {
        this.manager = manager;
        this.person = person;
        this.project = project;
        this.monthr = monthr;
        this.yearr = yearr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager m) {
        this.manager = m;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getMonth() {
        return monthr;
    }

    public void setMonth(int month) {
        this.monthr = month;
    }

    public int getYear() {
        return yearr;
    }

    public void setYear(int year) {
        this.yearr = year;
    }

    public void setSignM(){
        this.signM = true;
    }

    public void setSignR(){
        this.signR = true;
    }

    public boolean getSignM(){
        return signM;
    }

    public boolean getSignR(){
        return signR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature s = (Signature) o;
        return person==s.person && manager==s.manager && project==s.project && monthr==s.monthr && yearr==s.yearr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(person.username, manager.username, project.getProjectCode(), monthr, yearr);
    }

}
