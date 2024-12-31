package demo.repository;

import demo.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PendingProjectRepository extends CrudRepository<PendingProject, Long> {
    List<PendingProject> findByResearcher(Researcher researcher);
    List<PendingProject> findByManager(Manager manager);
    PendingProject findByProjectAndResearcher(Project project, Researcher researcher);

}
