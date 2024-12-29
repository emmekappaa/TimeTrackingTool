package demo.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;
    private String name;
    private String startDate;
    private String endDate;
    private String cup;
    private String projectCode;
    private String organizationName;

    //Un progetto è gestito da un solo manager.
    //Un manager può gestire molti progetti.
    @ManyToOne
    private Manager manager;

    //Un progetto può coinvolgere molti ricercatori.
    //Un ricercatore può partecipare a molti progetti.
    @ManyToMany
    private List<Researcher> researchers;

    protected Project(){}
    public Project(String name, String startDate, String endDate, Manager manager, List<Researcher> researchers, String projectCode, String organizationName, String cup) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manager = manager;
        this.researchers = researchers;
        this.projectCode = projectCode;
        this.organizationName = organizationName;
        this.cup = cup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getCup() {return cup;}

    public void setCup(String cup) {this.cup = cup;}

    public String getProjectCode() {return projectCode;}

    public void setProjectCode(String projectCode) {this.projectCode = projectCode;}

    public String getOrganizationName() {return organizationName;}

    public void setOrganizationName(String organizationName) {this.organizationName = organizationName;}

    public List<Researcher> getResearchers() {
        return researchers;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }
}
