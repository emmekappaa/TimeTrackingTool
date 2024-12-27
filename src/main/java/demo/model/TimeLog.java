package demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class TimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Project project;

    private LocalDate date;

    private double hoursWorked;

    public TimeLog() {}

    public TimeLog(Person person, Project project, LocalDate date, double hoursWorked) {
        this.person = person;
        this.project = project;
        this.date = date;
        this.hoursWorked = hoursWorked;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
