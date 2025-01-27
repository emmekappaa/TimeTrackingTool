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
    public void run(String... args) {

        System.out.println("Eseguo l'inizializzazione del database...");
        Researcher r1 = new Researcher("Dario","Rossi","user","ee11cbb19052e40b07aac0ca060c23ee", "LXQZJV78B10I098I");
        Researcher r2 = new Researcher("Luigi","Rossi","user1","24c9e15e52afc47c225b757e7bee1f9d", "LXQZJV78B10I098I");
        Manager m1 = new Manager("Franco","Verdi","root","63a9f0ea7bb98050796b649e85481845", "LYGZZH59P48B080B");
        r.save(r1);
        r.save(r2);
        r.save(m1);
        ArrayList<Researcher> list_r = new ArrayList<>();
        list_r.add(r1);
        list_r.add(r2);

        Project p1 = new Project("ProjectZomboide","29/04/2002","29/04/2025",m1,list_r, "PZ248597", "UNIVR", "98472683");
        Project p2 = new Project("Startx","29/04/2002","29/04/2025",m1,list_r, "SX216537", "UNIVR", "98472683");
        Project p3 = new Project("Marketprog","29/04/2002","29/04/2025",m1,list_r, "MK160397", "POLIMI", "98726789");
        Project p4 = new Project("FilesMans","29/04/2002","29/04/2025",m1,list_r, "FM083789", "UNIVR", "62794036");
        p.save(p1);
        p.save(p2);
        p.save(p3);
        p.save(p4);

        // Aggiunta TimeLog per gennaio 2025
        for (int day = 1; day <= 26; day++) {
            t.save(new TimeLog(r1, p1, LocalDate.of(2025, 1, day), 4));
            if (day % 2 == 0) t.save(new TimeLog(r1, p2, LocalDate.of(2025, 1, day), 3));
        }

        // Aggiunta TimeLog per novembre 2024
        for (int day = 1; day <= 30; day++) {
            t.save(new TimeLog(r1, p3, LocalDate.of(2024, 11, day), 5));
            if (day % 3 == 0) t.save(new TimeLog(r1, p4, LocalDate.of(2024, 11, day), 2));
        }

        // Aggiunta TimeLog per dicembre 2024
        for (int day = 1; day <= 31; day++) {

            if (day==25 || day==31)continue;

            t.save(new TimeLog(r1, p1, LocalDate.of(2024, 12, day), 1));
            if (day % 2 == 0) t.save(new TimeLog(r1, p3, LocalDate.of(2024, 12, day), 4));
        }


    }
}
