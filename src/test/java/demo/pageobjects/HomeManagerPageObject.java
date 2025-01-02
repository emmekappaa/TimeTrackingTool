package demo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class HomeManagerPageObject extends PageObject {

    // Costruttore
    public HomeManagerPageObject(WebDriver driver) {
        super(driver);
    }

    // Elementi della pagina

    @FindBy(id = "logout-link")
    private WebElement logoutLink;

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(id = "date")
    private WebElement date;

    @FindBy(css = "div[style*='color: red'] p")
    private WebElement errorMessage;

    @FindBy(name = "hoursWorked")
    private WebElement hoursWorkedInput;

    @FindBy(name = "projectId")
    private WebElement projectDropdown;

    @FindBy(css = "form[action='/homeManager/addTimeLog'] button")
    private WebElement addTimeLogButton;

    @FindBy(css = "tbody#work-hours-table tr")
    private List<WebElement> workHoursTableRows;

    @FindBy(css = "h3 + table tbody tr")
    private List<WebElement> hoursWorkedTodayRows;

    @FindBy(linkText = "View Projects List")
    private WebElement viewProjectsLink;

    @FindBy(linkText = "History Hours")
    private WebElement historyHoursLink;

    @FindBy(id = "monthly-report-link")
    private WebElement monthlyReportingLink;

    @FindBy(id = "monthly-report-modal")
    private WebElement monthlyReportModal;

    @FindBy(name = "researcherId")
    private WebElement researcherDropdown;

    @FindBy(id = "monthly-report-button")
    private WebElement confirmReportButton;

    // Metodi di interazione

    /**
     * Clicca sul link di logout.
     */
    public LoginPage clickLogout() {
        logoutLink.click();
        return new LoginPage(driver);
    }

    /**
     * Ottiene il nome utente visualizzato.
     * @return Nome utente come stringa.
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * Ottiene la data visualizzata.
     * @return Data come stringa.
     */
    public String getDate() {
        return date.getText();
    }

    /**
     * Ottiene il messaggio di errore (se presente).
     * @return Messaggio di errore come stringa.
     */
    public String getErrorMessage() {
        return errorMessage.getText();
    }

    /**
     * Inserisce le ore lavorate.
     * @param hours Numero di ore lavorate.
     */
    public void enterHoursWorked(String hours) {
        hoursWorkedInput.clear();
        hoursWorkedInput.sendKeys(hours);
    }

    /**
     * Seleziona un progetto dal dropdown.
     * @param projectValue Valore del progetto da selezionare.
     */
    public void selectProject(String projectValue) {
        projectDropdown.sendKeys(projectValue);
    }

    /**
     * Clicca sul pulsante per aggiungere un time log.
     */
    public void clickAddTimeLog() {
        addTimeLogButton.click();
    }

    /**
     * Ottiene le righe della tabella "Hours Worked Today".
     * @return Lista di elementi Web delle righe.
     */
    public List<WebElement> getHoursWorkedTodayRows() {
        return hoursWorkedTodayRows;
    }

    /**
     * Clicca sul link "View Projects List".
     */
    public ManagerProjectsPageObject clickViewProjectsLink() {
        viewProjectsLink.click();
        return new ManagerProjectsPageObject(driver);
    }

    /**
     * Clicca sul link "History Hours".
     */
    public void clickHistoryHoursLink() {
        historyHoursLink.click();
    }

    /**
     * Clicca sul link "Monthly Reporting".
     * @return True se il modale si apre correttamente, false altrimenti.
     */
    public boolean clickMonthlyReportingLink() {
        monthlyReportingLink.click();
        return monthlyReportModal.isDisplayed();
    }

    /**
     * Seleziona un ricercatore dal dropdown nella finestra modale.
     * @param researcherName Nome del ricercatore da selezionare.
     */
    public void selectResearcher(String researcherName) {
        researcherDropdown.sendKeys(researcherName);
    }

    /**
     * Conferma la selezione nella finestra modale.
     */
    public MonthlyReportPageObject clickConfirmReportButton() {
        confirmReportButton.click();
        return new MonthlyReportPageObject(driver);
    }
}
