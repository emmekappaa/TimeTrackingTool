package demo.tests;

import demo.model.Manager;
import demo.model.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ManagerTest {

    private Manager manager;
    private Project project1;
    private Project project2;

    @Before
    public void setUp() {
        // Creazione di oggetti di test per Manager e Progetto
        manager = new Manager("John", "Doe", "jdoe", "password123", "CF1234567890");

        // Creazione di progetti
        project1 = new Project("Project1", "2025-01-01", "2025-12-31", manager, null, "P001", "Org1", "CUP123");
        project2 = new Project("Project2", "2025-02-01", "2025-11-30", manager, null, "P002", "Org2", "CUP456");
    }

    @Test
    public void testManagerConstructor() {
        assertNotNull("Manager should not be null", manager);
        assertEquals("John", manager.getFirstName());
        assertEquals("Doe", manager.getLastName());
        assertEquals("jdoe", manager.getUsername());
        assertEquals("CF1234567890", manager.getCf());
    }

    @Test
    public void testAssignProjects() {
        // Aggiungo i progetti al manager
        manager.setAssignedProjects(Arrays.asList(project1, project2));

        // Verifica che i progetti siano stati assegnati correttamente
        List<Project> assignedProjects = manager.getAssignedProjects();
        assertNotNull(assignedProjects);
        assertEquals(2, assignedProjects.size());
        assertTrue(assignedProjects.contains(project1));
        assertTrue(assignedProjects.contains(project2));
    }

    @Test
    public void testManagerEquals() {
        Manager anotherManager = new Manager("Jane", "Smith", "jsmith", "password456", "CF0987654321");

        // Verifica che due manager con lo stesso username siano considerati uguali
        assertEquals(manager, manager);

        // Verifica che manager con username diversi non siano uguali
        assertNotEquals(manager, anotherManager);
    }

    @Test
    public void testManagerHashCode() {
        // Verifica che l'hashCode per manager con lo stesso username sia uguale
        Manager anotherManager = new Manager("John", "Doe", "jdoe", "password123", "CF1234567890");
        assertEquals(manager.hashCode(), anotherManager.hashCode());
    }
}
