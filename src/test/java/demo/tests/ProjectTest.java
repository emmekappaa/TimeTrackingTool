package demo.tests;

import demo.model.Project;
import demo.model.Researcher;
import org.junit.Before;
import org.junit.Test;
import demo.model.Manager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectTest {

    private Project project;
    private Manager manager;
    private Researcher researcher1;
    private Researcher researcher2;

    @Before
    public void setUp() {
        // Creazione di oggetti di test
        manager = new Manager("Alice", "Smith", "asmith", "password123", "CF001");
        researcher1 = new Researcher("John", "Doe", "jdoe", "password456", "CF002");
        researcher2 = new Researcher("Jane", "Doe", "jadoe", "password789", "CF003");

        // Creazione di un progetto
        project = new Project(
                "Project1",
                "2025-01-01",
                "2025-12-31",
                manager,
                Arrays.asList(researcher1, researcher2),
                "P001",
                "Organization1",
                "CUP001"
        );
    }

    @Test
    public void testProjectConstructor() {
        assertNotNull("Project should not be null", project);
        assertEquals("Project1", project.getName());
        assertEquals("2025-01-01", project.getStartDate());
        assertEquals("2025-12-31", project.getEndDate());
        assertEquals("P001", project.getProjectCode());
        assertEquals("CUP001", project.getCup());
        assertEquals("Organization1", project.getOrganizationName());
        assertEquals(manager, project.getManager());
        assertEquals(2, project.getResearchers().size());
        assertTrue(project.getResearchers().contains(researcher1));
        assertTrue(project.getResearchers().contains(researcher2));
    }

    @Test
    public void testSetAndGetManager() {
        Manager newManager = new Manager("Bob", "Brown", "bbrown", "password321", "CF004");
        project.setManager(newManager);
        assertEquals(newManager, project.getManager());
    }

    @Test
    public void testSetAndGetResearchers() {
        List<Researcher> newResearchers = Collections.singletonList(researcher1);
        project.setResearchers(newResearchers);
        assertEquals(1, project.getResearchers().size());
        assertTrue(project.getResearchers().contains(researcher1));
    }

    @Test
    public void testEquals() {
        Project sameProject = new Project(
                "AnotherProject",
                "2025-06-01",
                "2025-12-31",
                manager,
                null,
                "P001", // Identico projectCode
                "Organization2",
                "CUP002"
        );

        Project differentProject = new Project(
                "DifferentProject",
                "2025-06-01",
                "2025-12-31",
                manager,
                null,
                "P002", // Differente projectCode
                "Organization3",
                "CUP003"
        );

        assertTrue(project.equals(sameProject));
        assertFalse(project.equals(differentProject));
    }

    @Test
    public void testHashCode() {
        Project sameProject = new Project(
                "AnotherProject",
                "2025-06-01",
                "2025-12-31",
                manager,
                null,
                "P001", // Identico projectCode
                "Organization2",
                "CUP002"
        );

        assertEquals(project.hashCode(), sameProject.hashCode());
    }

    @Test
    public void testSetAndGetName() {
        project.setName("UpdatedProject");
        assertEquals("UpdatedProject", project.getName());
    }
}
