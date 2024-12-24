package demo;

import org.springframework.data.repository.CrudRepository;

public interface Repository  extends CrudRepository<Person, Long> {
    Person findByUsername(String u);
}
