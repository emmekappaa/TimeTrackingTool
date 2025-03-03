package demo.repository;


import demo.model.Manager;
import demo.model.Person;
import demo.model.Project;
import demo.model.Signature;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SignatureRepository extends CrudRepository<Signature, Long> {
    Optional<Signature> findByPersonAndProjectAndMonthrAndYearrAndManager(Person person, Project project, int monthr, int yearr, Manager m);
}
