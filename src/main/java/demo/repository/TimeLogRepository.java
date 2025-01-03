package demo.repository;

import demo.model.Person;
import demo.model.Project;
import demo.model.TimeLog;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface TimeLogRepository extends CrudRepository<TimeLog, Long> {

    Collection<TimeLog> findAllByPersonAndDate(Person loggedInUser, LocalDate today);
    Collection<TimeLog> findAllByPerson(Person loggedInUser);
}
