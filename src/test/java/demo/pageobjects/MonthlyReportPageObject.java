package demo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MonthlyReportPageObject extends PageObject {

    // Costruttore
    public MonthlyReportPageObject(WebDriver driver) {
        super(driver);
    }

    // Elementi della pagina

    @FindBy(id = "back-button")
    private WebElement backButton;

    @FindBy(id = "selectedProject")
    private WebElement projectDropdown;

    @FindBy(id = "month")
    private WebElement monthDropdown;

    @FindBy(id = "year")
    private WebElement yearInput;

    @FindBy(css = "form[action='/monthly/report'] button[type='submit']")
    private WebElement updateButton;

    @FindBy(css = "div.header-info p:nth-child(1) span")
    private List<WebElement> researcherNameAndSurname;

    @FindBy(css = "div.header-info p:nth-child(2) span")
    private WebElement fiscalCode;

    @FindBy(css = "div.header-info p:nth-child(3) span")
    private WebElement projectTitle;

    @FindBy(css = "div.header-info p:nth-child(4) span")
    private WebElement cup;

    @FindBy(css = "div.header-info p:nth-child(5) span")
    private WebElement projectCode;

    @FindBy(css = "div.header-info p:nth-child(6) span")
    private WebElement organizationName;

    @FindBy(css = "table thead tr th")
    private List<WebElement> tableHeaders;

    @FindBy(css = "table tbody tr:nth-child(1) td")
    private List<WebElement> projectHoursRow;

    @FindBy(css = "table tbody tr:nth-child(2) td")
    private List<WebElement> otherProjectsHoursRow;

    @FindBy(css = "table tbody tr:nth-child(3) td")
    private List<WebElement> otherProjectsSameOrgHoursRow;

    @FindBy(css = "table tbody tr:nth-child(4) td")
    private List<WebElement> totalHoursRow;

    @FindBy(id = "researcher-signature")
    private WebElement researcherSignature;

    @FindBy(id = "researcher-sign-button")
    private WebElement researcherSignButton;

    @FindBy(id = "manager-signature")
    private WebElement managerSignature;

    @FindBy(id = "manager-sign-button")
    private WebElement managerSignButton;

    // Metodi di interazione

    /**
     * Clicca sul pulsante "Back to Home".
     */
    public HomeResearcherPageObject clickBackButton() {
        backButton.click();
        return new HomeResearcherPageObject(driver);
    }

    /**
     * Seleziona un progetto dal dropdown.
     * @param projectName Nome del progetto da selezionare.
     */
    public void selectProject(String projectName) {
        projectDropdown.sendKeys(projectName);
    }

    /**
     * Seleziona un mese dal dropdown.
     * @param month Numero del mese (1-12).
     */
    public void selectMonth(int month) {
        monthDropdown.sendKeys(String.valueOf(month));
    }

    /**
     * Inserisce l'anno.
     * @param year Anno da inserire.
     */
    public void enterYear(int year) {
        yearInput.clear();
        yearInput.sendKeys(String.valueOf(year));
    }

    /**
     * Clicca sul pulsante "Update".
     */
    public void clickUpdateButton() {
        updateButton.click();
    }

    /**
     * Ottiene il nome e cognome del ricercatore.
     * @return Stringa concatenata con nome e cognome.
     */
    public String getResearcherNameAndSurname() {
        return researcherNameAndSurname.get(0).getText() + " " + researcherNameAndSurname.get(1).getText();
    }

    /**
     * Ottiene il codice fiscale.
     * @return Stringa con il codice fiscale.
     */
    public String getFiscalCode() {
        return fiscalCode.getText();
    }

    /**
     * Ottiene il titolo del progetto.
     * @return Stringa con il titolo del progetto.
     */
    public String getProjectTitle() {
        return projectTitle.getText();
    }

    /**
     * Ottiene il CUP del progetto.
     * @return Stringa con il CUP.
     */
    public String getCup() {
        return cup.getText();
    }

    /**
     * Ottiene il codice del progetto.
     * @return Stringa con il codice del progetto.
     */
    public String getProjectCode() {
        return projectCode.getText();
    }

    /**
     * Ottiene il nome dell'organizzazione.
     * @return Stringa con il nome dell'organizzazione.
     */
    public String getOrganizationName() {
        return organizationName.getText();
    }

    /**
     * Ottiene i valori della riga "Project Hours".
     * @return Lista di stringhe con le ore di progetto.
     */
    public List<String> getProjectHours() {
        return projectHoursRow.stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Ottiene i valori della riga "Other Projects Hours".
     * @return Lista di stringhe con le ore di altri progetti.
     */
    public List<String> getOtherProjectsHours() {
        return otherProjectsHoursRow.stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Ottiene i valori della riga "Other Projects Hours Same Organization".
     * @return Lista di stringhe con le ore di altri progetti nella stessa organizzazione.
     */
    public List<String> getOtherProjectsSameOrgHours() {
        return otherProjectsSameOrgHoursRow.stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Ottiene i valori della riga "Total Hours".
     * @return Lista di stringhe con le ore totali.
     */
    public List<String> getTotalHours() {
        return totalHoursRow.stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Clicca sul pulsante di firma del ricercatore.
     */
    public void clickResearcherSignButton() {
        researcherSignButton.click();
    }

    /**
     * Ottiene la firma del ricercatore.
     * @return Stringa con la firma del ricercatore.
     */
    public String getResearcherSignature() {
        return researcherSignature.getText();
    }

    /**
     * Clicca sul pulsante di firma del manager.
     */
    public void clickManagerSignButton() {
        managerSignButton.click();
    }

    /**
     * Ottiene la firma del manager.
     * @return Stringa con la firma del manager.
     */
    public String getManagerSignature() {
        return managerSignature.getText();
    }
}
