package demo.tests;

import demo.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignatureTest {

    private Signature signature;
    private Person person;
    private Manager manager;
    private Project project;

    @Before
    public void setUp() {
        // Creazione di oggetti di test
        person = new Researcher("John", "Doe", "jdoe", "password456", "CF002");
        manager = new Manager("Alice", "Smith", "asmith", "password123", "CF001");
        project = new Project(
                "Project1",
                "2025-01-01",
                "2025-12-31",
                manager,
                null,
                "P001",
                "Organization1",
                "CUP001"
        );

        // Creazione di una firma
        signature = new Signature(person, manager, project, 1, 2025);
    }

    @Test
    public void testSignatureConstructor() {
        assertNotNull("Signature should not be null", signature);
        assertEquals(person, signature.getPerson());
        assertEquals(manager, signature.getManager());
        assertEquals(project, signature.getProject());
        assertEquals(1, signature.getMonth());
        assertEquals(2025, signature.getYear());
        assertFalse(signature.getSignM());
        assertFalse(signature.getSignR());
    }

    @Test
    public void testSetAndGetPerson() {
        Person newPerson = new Researcher("Jane", "Doe", "jadoe", "password789", "CF003");
        signature.setPerson(newPerson);
        assertEquals(newPerson, signature.getPerson());
    }

    @Test
    public void testSetAndGetManager() {
        Manager newManager = new Manager("Bob", "Brown", "bbrown", "password321", "CF004");
        signature.setManager(newManager);
        assertEquals(newManager, signature.getManager());
    }

    @Test
    public void testSetAndGetProject() {
        Project newProject = new Project(
                "Project2",
                "2025-06-01",
                "2025-12-31",
                manager,
                null,
                "P002",
                "Organization2",
                "CUP002"
        );
        signature.setProject(newProject);
        assertEquals(newProject, signature.getProject());
    }

    @Test
    public void testSetAndGetMonth() {
        signature.setMonth(12);
        assertEquals(12, signature.getMonth());
    }

    @Test
    public void testSetAndGetYear() {
        signature.setYear(2030);
        assertEquals(2030, signature.getYear());
    }

    @Test
    public void testSetAndGetSignM() {
        assertFalse("Manager signature should initially be false", signature.getSignM());
        signature.setSignM();
        assertTrue("Manager signature should be true after signing", signature.getSignM());
    }

    @Test
    public void testSetAndGetSignR() {
        assertFalse("Researcher signature should initially be false", signature.getSignR());
        signature.setSignR();
        assertTrue("Researcher signature should be true after signing", signature.getSignR());
    }

    @Test
    public void testEquals() {
        Signature sameSignature = new Signature(person, manager, project, 1, 2025);
        Signature differentSignature = new Signature(person, manager, project, 2, 2025);

        assertEquals(signature, sameSignature);
        assertNotEquals(signature, differentSignature);
    }

    @Test
    public void testHashCode() {
        Signature sameSignature = new Signature(person, manager, project, 1, 2025);
        assertEquals(signature.hashCode(), sameSignature.hashCode());
    }
}
