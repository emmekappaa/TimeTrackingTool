package demo.tests;

import demo.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TimeLogTest {

    private TimeLog timeLog;
    private Person person;
    private Project project;
    private LocalDate date;

    @Before
    public void setUp() {
        // Creazione di oggetti di test
        person = new Researcher("John", "Doe", "jdoe", "password456", "CF002");
        project = new Project(
                "Project1",
                "2025-01-01",
                "2025-12-31",
                new Manager("Alice", "Smith", "asmith", "password123", "CF001"),
                null,
                "P001",
                "Organization1",
                "CUP001"
        );
        date = LocalDate.of(2025, 1, 1);

        // Creazione di un TimeLog
        timeLog = new TimeLog(person, project, date, 8.0);
    }

    @Test
    public void testTimeLogConstructor() {
        assertNotNull("TimeLog should not be null", timeLog);
        assertEquals(person, timeLog.getPerson());
        assertEquals(project, timeLog.getProject());
        assertEquals(date, timeLog.getDate());
        assertEquals(8.0, timeLog.getHoursWorked(), 0.01);
    }

    @Test
    public void testSetAndGetPerson() {
        Person newPerson = new Researcher("Jane", "Doe", "jadoe", "password789", "CF003");
        timeLog.setPerson(newPerson);
        assertEquals(newPerson, timeLog.getPerson());
    }

    @Test
    public void testSetAndGetProject() {
        Project newProject = new Project(
                "Project2",
                "2025-06-01",
                "2025-12-31",
                new Manager("Bob", "Brown", "bbrown", "password321", "CF004"),
                null,
                "P002",
                "Organization2",
                "CUP002"
        );
        timeLog.setProject(newProject);
        assertEquals(newProject, timeLog.getProject());
    }

    @Test
    public void testSetAndGetDate() {
        LocalDate newDate = LocalDate.of(2025, 12, 31);
        timeLog.setDate(newDate);
        assertEquals(newDate, timeLog.getDate());
    }

    @Test
    public void testSetAndGetHoursWorked() {
        timeLog.setHoursWorked(10.5);
        assertEquals(10.5, timeLog.getHoursWorked(), 0.01);
    }

    @Test
    public void testEquals() {
        TimeLog sameTimeLog = new TimeLog(person, project, date, 8.0);
        TimeLog differentTimeLog = new TimeLog(person, project, LocalDate.of(2025, 2, 1), 8.0);

        assertTrue(timeLog.equals(sameTimeLog));
        assertFalse(timeLog.equals(differentTimeLog));
    }

    @Test
    public void testHashCode() {
        TimeLog sameTimeLog = new TimeLog(person, project, date, 8.0);
        assertEquals(timeLog.hashCode(), sameTimeLog.hashCode());
    }
}
