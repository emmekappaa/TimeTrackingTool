package demo;

import jakarta.persistence.Access;
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
        r.save(new Researcher("Mario","Rossi","MrRs","Verdi111"));
        for(Person r1: r.findAll()){
            System.out.println(r1.getFirstName());
        }
    }
}
