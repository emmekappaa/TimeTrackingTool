package demo.tests;

import demo.pageobjects.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SeleniumTest extends BaseTest {

    @Test
    public void testLoginAsResearcher() {
        navigateToHomePage();
        LoginPage loginPage = new LoginPage(driver);
        HomeResearcherPageObject h = loginPage.loginResearcher("user", "user");

        assertEquals(h.getUsername(), "Dario Rossi");
    }

    @Test
    public void testAddTimeLog() {
        navigateToHomePage();
        HomeResearcherPageObject h = loginAsResearcher();

        h.enterHoursWorked("4");
        h.selectProject("ProjectZomboide");
        h.clickAddTimeLog();

        boolean timeLogAdded = h.getHoursWorkedTodayRows().stream()
                .anyMatch(row -> row.getText().contains("ProjectZomboide") && row.getText().contains("4"));
        assertTrue("Il time log non è stato aggiunto correttamente.", timeLogAdded);
    }

    @Test
    public void testErrorOnExceedingMaxHours() {
        navigateToHomePage();
        HomeResearcherPageObject h = loginAsResearcher();

        h.enterHoursWorked("9");
        h.selectProject("Startx");
        h.clickAddTimeLog();

        String errorMessage = h.getErrorMessage();
        assertEquals("You cannot log more than 8 working hours in a day.", errorMessage);
    }

    @Test
    public void testMonthlyReport() {
        navigateToHomePage();
        HomeResearcherPageObject h = loginAsResearcher();
        MonthlyReportPageObject m = h.clickMonthlyReportingLink();

        m.selectProject("Startx");
        m.selectMonth(12);
        m.enterYear(2024);
        m.clickUpdateButton();

        assertEquals("Startx", m.getProjectTitle());
        assertEquals("98472683", m.getCup());
        assertEquals("SX216537", m.getProjectCode());
        assertEquals("UNIVR", m.getOrganizationName());

        m.clickResearcherSignButton();
        String researcherSignatureAfter = m.getResearcherSignature();
        assertEquals("Dario Rossi", researcherSignatureAfter);

        h = (HomeResearcherPageObject) m.clickBackButton("R");

        h.clickLogout();

        HomeManagerPageObject hm = loginAsManager();

        hm.clickMonthlyReportingLink();
        hm.selectResearcher("Dario Rossi");
        MonthlyReportPageObject m1 = hm.clickConfirmReportButton();

        m1.selectProject("Startx");
        m1.selectMonth(12);
        m1.enterYear(2024);
        m1.clickUpdateButton();

        assertEquals("Startx", m1.getProjectTitle());
        assertEquals("98472683", m1.getCup());
        assertEquals("SX216537", m1.getProjectCode());
        assertEquals("UNIVR", m1.getOrganizationName());

        m1.clickManagerSignButton();

        researcherSignatureAfter = m1.getResearcherSignature();
        assertEquals("Dario Rossi", researcherSignatureAfter);

        String managerSignatureAfter = m.getManagerSignature();
        assertEquals("Franco Verdi", managerSignatureAfter);
    }

    @Test
    public void testManagerFlow() {
        navigateToHomePage();

        HomeManagerPageObject hm = loginAsManager();
        WebElement user = driver.findElement(By.id("username"));
        assertEquals(user.getText(), "Franco Verdi");

        hm.enterHoursWorked("3");
        hm.selectProject("Startx");
        hm.clickAddTimeLog();

        boolean timeLogAdded = hm.getHoursWorkedTodayRows().stream()
                .anyMatch(row -> row.getText().contains("Startx") && row.getText().contains("3"));
        assertTrue("Il time log non è stato aggiunto correttamente.", timeLogAdded);

        hm.enterHoursWorked("6");
        hm.selectProject("ProjectZomboide");
        hm.clickAddTimeLog();

        String errorMessage = hm.getErrorMessage();
        assertEquals("You cannot log more than 8 working hours in a day.", errorMessage);
    }

    @Test
    public void testResearcherAcceptsPendingProject() {
        navigateToHomePage();
        HomeManagerPageObject hm = loginAsManager();
        ManagerProjectsPageObject mpp = hm.clickViewProjectsLink();
        mpp.enterProjectName("Test");
        mpp.enterStartDate("2024-12-20");
        mpp.enterEndDate("2025-12-20");
        mpp.enterCup("000111");
        mpp.enterProjectCode("123321");
        mpp.enterOrganizationName("UNIVR");
        ArrayList<String> a = new ArrayList<>();
        a.add("Dario Rossi");
        a.add("Luigi Rossi");
        mpp.selectResearchers(a);
        mpp.clickAssignProjectButton();
        assertTrue("Il progetto non è stato aggiunto correttamente.", mpp.isProjectPresent("Test"));
        hm = mpp.clickBackLink();
        LoginPage l = hm.clickLogout();
        HomeResearcherPageObject h = l.loginResearcher("user", "user");
        ResearcherProjectsPageObject r = h.clickViewProjectsLink();

        r.acceptPendingProject("Test");

        List<String> activeProjectNames = r.getActiveProjectNames();
        assertTrue("Il progetto 'Test' non è stato trasferito tra i progetti attivi.", activeProjectNames.contains("Test"));
        assertTrue("Il progetto 'Test' è ancora nella lista dei progetti pendenti.", r.arePendingProjectThere());
    }

    @Test
    public void testAddTwoHoursAndVerifyInHistory() {

        navigateToHomePage();
        HomeResearcherPageObject h = loginAsResearcher();
        h.enterHoursWorked("1");
        h.selectProject("Marketprog");
        h.clickAddTimeLog();
        HoursHistoryPageObject h1 = h.clickHistoryHoursLink();
        List<WebElement> historyRows = h1.getTableRows();

        boolean timeLogFound = historyRows.stream()
                .anyMatch(row -> row.getText().contains("Marketprog") && row.getText().contains("1"));

        assertTrue("Il time log con 1 ore non è stato aggiunto correttamente nella cronologia.", timeLogFound);
    }



    private void navigateToHomePage() {
        driver.get("http://localhost:8080");
    }

    private HomeResearcherPageObject loginAsResearcher() {
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.loginResearcher("user", "user");
    }

    private HomeManagerPageObject loginAsManager() {
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.loginManager("root", "root");
    }
}
