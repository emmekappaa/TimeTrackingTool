package demo.config;

import demo.model.Manager;
import demo.model.Person;
import demo.repository.Repository;
import demo.model.Researcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    Repository r;
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Eseguo l'inizializzazione del database...");
        r.save(new Researcher("Dario","Rossi","user","user"));
        r.save(new Manager("Franco","Verdi","root","root"));
        /*
        for(Person r1: r.findAll()){
            System.out.println(r1.getFirstName());
        }
        */
    }
}
