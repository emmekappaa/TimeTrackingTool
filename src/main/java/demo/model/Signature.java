package demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
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

    private int monthr;

    private int yearr;

    public Signature() {}

    public Signature(Person person, Project project, int monthr, int yearr) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature s = (Signature) o;
        return id == s.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
