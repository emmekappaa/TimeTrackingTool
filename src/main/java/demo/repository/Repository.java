package demo.repository;

import demo.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface Repository  extends CrudRepository<Person, Long> {
    Person findByUsername(String u);
}
