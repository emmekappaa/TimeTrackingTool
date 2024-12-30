package demo.config;

import demo.model.*;
import demo.repository.ProjectRepository;
import demo.repository.Repository;
import demo.repository.TimeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    Repository r;

    @Autowired
    TimeLogRepository t;

    @Autowired
    ProjectRepository p;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Eseguo l'inizializzazione del database...");
        Researcher r1 = new Researcher("Dario","Rossi","user","user", "LXQZJV78B10I098I");
        Manager m1 = new Manager("Franco","Verdi","root","root", "LYGZZH59P48B080B");
        r.save(r1);
        r.save(m1);
        ArrayList<Researcher> list_r = new ArrayList<>();
        list_r.add(r1);
        /*
        for(Person r1: r.findAll()){
            System.out.println(r1.getFirstName());
        }
        */
        Project p1 = new Project("ProjectZomboide","29/04/2002","29/04/2025",m1,list_r, "PZ248597", "UNIVR", "98472683");
        Project p2 = new Project("Startx","29/04/2002","29/04/2025",m1,list_r, "SX216537", "UNIVR", "98472683");
        Project p3 = new Project("Marketprog","29/04/2002","29/04/2025",m1,list_r, "MK160397", "CASADINONNA", "98726789");
        Project p4 = new Project("FilesMans","29/04/2002","29/04/2025",m1,list_r, "FM083789", "UNIVR", "62794036");
        p.save(p1);
        p.save(p2);
        p.save(p3);
        p.save(p4);
        //t.save(new TimeLog(r1, p1, LocalDate.of(2024,12,18), 4));
        //t.save(new TimeLog(r1, p3, LocalDate.of(2014,10,1), 7));

        // Aggiunta TimeLog per ottobre 2024
        for (int day = 1; day <= 31; day++) {
            t.save(new TimeLog(r1, p1, LocalDate.of(2024, 10, day), 4));
            if (day % 2 == 0) t.save(new TimeLog(r1, p2, LocalDate.of(2024, 10, day), 3));
        }

        // Aggiunta TimeLog per novembre 2024
        for (int day = 1; day <= 30; day++) {
            t.save(new TimeLog(r1, p3, LocalDate.of(2024, 11, day), 5));
            if (day % 3 == 0) t.save(new TimeLog(r1, p4, LocalDate.of(2024, 11, day), 2));
        }

        // Aggiunta TimeLog per dicembre 2024
        for (int day = 1; day <= 31; day++) {
            t.save(new TimeLog(r1, p1, LocalDate.of(2024, 12, day), 6));
            if (day % 2 == 0) t.save(new TimeLog(r1, p3, LocalDate.of(2024, 12, day), 4));
        }

        //t.save(new TimeLog(r1, p1, LocalDate.of(2024, 1, 15), 6));
        //t.save(new TimeLog(r1, p3, LocalDate.of(2023, 3, 10), 7));
    }
}
