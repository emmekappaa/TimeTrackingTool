package demo.repository;

import demo.model.Project;
import demo.model.Researcher;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;


public interface ProjectRepository extends CrudRepository<Project, Long> {
    ArrayList<Project> findByResearchersContains(Researcher researcher);
    Project findByName(String name);
    ArrayList<Project> findByResearchersContainsAndOrganizationName(Researcher researcher, String organizationName);
}
