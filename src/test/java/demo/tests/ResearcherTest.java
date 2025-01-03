package demo.tests;

import demo.model.Project;
import demo.model.Researcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ResearcherTest {

    private Researcher researcher;
    private Project project1;
    private Project project2;
    private List<Project> participatingProjects;
    private List<Project> addedProjects;

    @Before
    public void setUp() {
        // Crea un ricercatore di esempio
        researcher = new Researcher("John", "Doe", "johndoe", "password123", "CF12345");

        // Crea progetti di esempio
        project1 = new Project("test", "date", "date", null, null, "code", "org", "37141");
        project1.setName("Project 1");

        project2 = new Project("test", "date", "date", null, null, "code", "org", "37141");
        project2.setName("Project 2");

        // Inizializza le liste di progetti
        participatingProjects = new ArrayList<>();
        addedProjects = new ArrayList<>();

        participatingProjects.add(project1);
        addedProjects.add(project2);

        // Imposta le liste di progetti
        researcher.setParticipatingProjects(participatingProjects);
        researcher.setAddedProjects(addedProjects);
    }

    @Test
    public void testConstructor() {
        assertEquals("John", researcher.getFirstName());
        assertEquals("Doe", researcher.getLastName());
        assertEquals("johndoe", researcher.getUsername());
        assertEquals("password123", researcher.getPassword());
        assertEquals("CF12345", researcher.getCf());
    }

    @Test
    public void testSettersAndGetters() {
        researcher.setFirstName("Jane");
        assertEquals("Jane", researcher.getFirstName());

        researcher.setLastName("Smith");
        assertEquals("Smith", researcher.getLastName());

        researcher.setUsername("janesmith");
        assertEquals("janesmith", researcher.getUsername());

        researcher.setPassword("newpassword");
        assertEquals("newpassword", researcher.getPassword());

        researcher.setCf("NEWCF123");
        assertEquals("NEWCF123", researcher.getCf());
    }

    @Test
    public void testParticipatingProjects() {
        List<Project> projects = researcher.getParticipatingProjects();
        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Project 1", projects.get(0).getName());

        // Aggiungi un altro progetto
        Project newProject = new Project("test", "date", "date", null, null, "code", "org", "37141");
        newProject.setName("Project 3");
        projects.add(newProject);
        researcher.setParticipatingProjects(projects);

        assertEquals(2, researcher.getParticipatingProjects().size());
    }

    @Test
    public void testAddedProjects() {
        List<Project> projects = researcher.getAddedProjects();
        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Project 2", projects.get(0).getName());

        // Rimuovi un progetto
        projects.remove(0);
        researcher.setAddedProjects(projects);

        assertEquals(0, researcher.getAddedProjects().size());
    }

    @Test
    public void testEqualsAndHashCode() {
        Researcher anotherResearcher = new Researcher("Jane", "Smith", "janesmith", "password456", "CF67890");

        assertEquals(researcher, researcher);
        assertNotEquals(researcher, anotherResearcher);
        assertEquals(researcher.hashCode(), researcher.hashCode());
        assertNotEquals(researcher.hashCode(), anotherResearcher.hashCode());
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        assertNotEquals(researcher, null);
        assertNotEquals(researcher, new Object());
    }
}
