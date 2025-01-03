package demo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomeResearcherPageObject extends PageObject {

    // Costruttore
    public HomeResearcherPageObject(WebDriver driver) {
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

    @FindBy(id = "hoursWorked")
    private WebElement hoursWorkedInput;

    @FindBy(name = "projectId")
    private WebElement projectDropdown;

    @FindBy(css = "form[action='/homeResearcher/addTimeLog'] button")
    private WebElement addTimeLogButton;

    @FindBy(css = "tbody#work-hours-table tr")
    private List<WebElement> workHoursTableRows;

    @FindBy(css = "h3 + table tbody tr")
    private List<WebElement> hoursWorkedTodayRows;

    @FindBy(linkText = "View Projects List")
    private WebElement viewProjectsLink;

    @FindBy(linkText = "History Hours")
    private WebElement historyHoursLink;

    @FindBy(linkText = "Monthly Reporting")
    private WebElement monthlyReportingLink;

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
    public ResearcherProjectsPageObject clickViewProjectsLink() {
        viewProjectsLink.click();
        return new ResearcherProjectsPageObject(driver);
    }

    /**
     * Clicca sul link "History Hours".
     */
    public HoursHistoryPageObject clickHistoryHoursLink() {
        historyHoursLink.click();
        return new HoursHistoryPageObject(driver);
    }

    /**
     * Clicca sul link "Monthly Reporting".
     */
    public MonthlyReportPageObject clickMonthlyReportingLink() {
        monthlyReportingLink.click();
        return new MonthlyReportPageObject(driver);
    }
}
