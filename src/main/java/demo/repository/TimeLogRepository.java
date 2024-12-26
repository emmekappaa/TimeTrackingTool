package demo.repository;

import demo.model.TimeLog;
import org.springframework.data.repository.CrudRepository;

public interface TimeLogRepository extends CrudRepository<TimeLog, Long> {

}
