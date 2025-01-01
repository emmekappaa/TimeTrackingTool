package demo.tests;

import demo.pageobjects.HomeManagerPageObject;
import demo.pageobjects.HomeResearcherPageObject;
import demo.pageobjects.LoginPage;
import demo.pageobjects.MonthlyReportPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class SeleniumTest extends BaseTest {

    @Test
    public void testing(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        HomeResearcherPageObject h = loginPage.loginResearcher("user", "user");
        // Verifica che l'URL sia quello atteso dopo il login
        WebElement username = driver.findElement(By.id("username"));
        assertEquals(username.getText(), "Dario Rossi");
        h.enterHoursWorked("4");
        h.selectProject("ProjectZomboide");
        h.clickAddTimeLog();
        // Verifica che il "time log" sia stato aggiunto correttamente
        boolean timeLogAdded = h.getHoursWorkedTodayRows().stream()
                .anyMatch(row -> row.getText().contains("ProjectZomboide") && row.getText().contains("4"));
        assertTrue("Il time log non è stato aggiunto correttamente.", timeLogAdded);
        h.enterHoursWorked("5");
        h.selectProject("Startx");
        h.clickAddTimeLog();
        // Cattura il messaggio di errore
        String errorMessage = h.getErrorMessage();

        // Asserzione sul messaggio di errore
        assertEquals("You cannot log more than 8 working hours in a day.", errorMessage);

        MonthlyReportPageObject m = h.clickMonthlyReportingLink();
        // Seleziona il progetto "Startx"
        m.selectProject("Startx");
        // Seleziona il mese Dicembre
        m.selectMonth(12);
        // Inserisce l'anno 2024
        m.enterYear(2024);
        // Clicca sul pulsante "Update"
        m.clickUpdateButton();
        assertEquals("Startx", m.getProjectTitle());
        assertEquals("98472683", m.getCup());
        assertEquals("SX216537", m.getProjectCode());
        assertEquals("UNIVR", m.getOrganizationName());

        // Esegue l'azione di firma da parte del ricercatore
        m.clickResearcherSignButton();

        String researcherSignatureAfter = m.getResearcherSignature();
        assertEquals("Dario Rossi", researcherSignatureAfter);

        h = m.clickBackButton();
        LoginPage l = h.clickLogout();
        HomeManagerPageObject hm = l.loginManager("root", "root");
        WebElement user = driver.findElement(By.id("username"));
        assertEquals(user.getText(), "Franco Verdi");
        hm.enterHoursWorked("3");
        hm.selectProject("Startx");
        hm.clickAddTimeLog();
        // Verifica che il "time log" sia stato aggiunto correttamente
        timeLogAdded = h.getHoursWorkedTodayRows().stream()
                .anyMatch(row -> row.getText().contains("Startx") && row.getText().contains("3"));
        assertTrue("Il time log non è stato aggiunto correttamente.", timeLogAdded);
        hm.enterHoursWorked("6");
        hm.selectProject("ProjectZomboide");
        hm.clickAddTimeLog();
        // Cattura il messaggio di errore
        errorMessage = hm.getErrorMessage();

        // Asserzione sul messaggio di errore
        assertEquals("You cannot log more than 8 working hours in a day.", errorMessage);
        hm.clickMonthlyReportingLink();
        hm.selectResearcher("Dario Rossi");
        m = hm.clickConfirmReportButton();
        // Seleziona il progetto "Startx"
        m.selectProject("Startx");
        // Seleziona il mese Dicembre
        m.selectMonth(12);
        // Inserisce l'anno 2024
        m.enterYear(2024);
        // Clicca sul pulsante "Update"
        m.clickUpdateButton();
        assertEquals("Startx", m.getProjectTitle());
        assertEquals("98472683", m.getCup());
        assertEquals("SX216537", m.getProjectCode());
        assertEquals("UNIVR", m.getOrganizationName());
        m.clickManagerSignButton();

        researcherSignatureAfter = m.getResearcherSignature();
        assertEquals("Dario Rossi", researcherSignatureAfter);

        String ManagerSignatureAfter = m.getManagerSignature();
        assertEquals("Franco Verdi", ManagerSignatureAfter);

    }
}
