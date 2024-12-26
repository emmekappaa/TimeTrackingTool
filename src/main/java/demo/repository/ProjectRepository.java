package demo.repository;

import demo.model.Project;
import org.springframework.data.repository.CrudRepository;


public interface ProjectRepository extends CrudRepository<Project, Long> {

}
