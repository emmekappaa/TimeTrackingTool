package demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;


public class ManagerProjectsPageObject extends PageObject {

    // Costruttore
    public ManagerProjectsPageObject(WebDriver driver) {
        super(driver);
    }

    // Elementi della pagina

    @FindBy(css = "table tbody tr")
    private List<WebElement> projectRows;

    @FindBy(id = "back-link")
    private WebElement backLink;

    @FindBy(id = "name")
    private WebElement projectNameInput;

    @FindBy(id = "startDate")
    private WebElement startDateInput;

    @FindBy(id = "endDate")
    private WebElement endDateInput;

    @FindBy(id = "cup")
    private WebElement cupInput;

    @FindBy(id = "projectCode")
    private WebElement projectCodeInput;

    @FindBy(id = "organizationName")
    private WebElement organizationNameInput;

    @FindBy(id = "researcherId")
    private WebElement researcherDropdown;

    @FindBy(css = "button[type='submit']")
    private WebElement assignProjectButton;

    // Metodi di interazione

    /**
     * Torna alla home manager.
     */
    public HomeManagerPageObject clickBackLink() {
        backLink.click();
        return new HomeManagerPageObject(driver);
    }

    /**
     * Inserisce il nome del progetto.
     * @param projectName Nome del progetto.
     */
    public void enterProjectName(String projectName) {
        projectNameInput.clear();
        projectNameInput.sendKeys(projectName);
    }

    /**
     * Inserisce la data di inizio.
     * @param startDate Data di inizio (formato YYYY-MM-DD).
     */
    public void enterStartDate(String startDate) {
        startDateInput.clear();
        startDateInput.sendKeys(startDate);
    }

    /**
     * Inserisce la data di fine.
     * @param endDate Data di fine (formato YYYY-MM-DD).
     */
    public void enterEndDate(String endDate) {
        endDateInput.clear();
        endDateInput.sendKeys(endDate);
    }

    /**
     * Inserisce il CUP.
     * @param cup CUP del progetto.
     */
    public void enterCup(String cup) {
        cupInput.clear();
        cupInput.sendKeys(cup);
    }

    /**
     * Inserisce il codice del progetto.
     * @param projectCode Codice del progetto.
     */
    public void enterProjectCode(String projectCode) {
        projectCodeInput.clear();
        projectCodeInput.sendKeys(projectCode);
    }

    /**
     * Inserisce il nome dell'organizzazione.
     * @param organizationName Nome dell'organizzazione.
     */
    public void enterOrganizationName(String organizationName) {
        organizationNameInput.clear();
        organizationNameInput.sendKeys(organizationName);
    }

    /**
     * Seleziona un ricercatore dal menu a tendina.
     * @param researcherName Nome del ricercatore.
     */
    public void selectResearcher(String researcherName) {
        researcherDropdown.sendKeys(researcherName);
    }

    /**
     * Clicca sul pulsante per assegnare il progetto.
     */
    public void clickAssignProjectButton() {
        assignProjectButton.click();
    }

    /**
     * Verifica se un progetto con il nome specificato è presente nella lista dei progetti attivi.
     * @param projectName Nome del progetto da cercare.
     * @return true se il progetto è presente, false altrimenti.
     */
    public boolean isProjectPresent(String projectName) {
        return projectRows.stream()
                .anyMatch(row -> row.getText().trim().equals(projectName));
    }

}
