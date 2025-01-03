package demo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HoursHistoryPageObject extends PageObject {

    // Costruttore
    public HoursHistoryPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "back-button")
    private WebElement backButton;

    @FindBy(css = "table thead tr th")
    private List<WebElement> tableHeaders;

    @FindBy(css = "table tbody tr")
    private List<WebElement> tableRows;

    @FindBy(css = "table tbody tr td:nth-child(1)")
    private List<WebElement> dateColumn;

    @FindBy(css = "table tbody tr td:nth-child(2)")
    private List<WebElement> projectColumn;

    @FindBy(css = "table tbody tr td:nth-child(3)")
    private List<WebElement> hoursWorkedColumn;

    // Metodi di interazione

    /**
     * Clicca sul pulsante "Back to Home".
     */
    public void clickBackButton() {
        backButton.click();
    }

    /**
     * Ottiene l'elenco delle intestazioni della tabella.
     * @return Lista di elementi Web delle intestazioni.
     */
    public List<WebElement> getTableHeaders() {
        return tableHeaders;
    }

    /**
     * Ottiene tutte le righe della tabella.
     * @return Lista di elementi Web delle righe della tabella.
     */
    public List<WebElement> getTableRows() {
        return tableRows;
    }

    /**
     * Ottiene la lista di date dalla colonna "Date".
     * @return Lista di elementi Web delle date.
     */
    public List<WebElement> getDateColumn() {
        return dateColumn;
    }

    /**
     * Ottiene la lista dei progetti dalla colonna "Project".
     * @return Lista di elementi Web dei progetti.
     */
    public List<WebElement> getProjectColumn() {
        return projectColumn;
    }

    /**
     * Ottiene la lista delle ore lavorate dalla colonna "Hours Worked".
     * @return Lista di elementi Web delle ore lavorate.
     */
    public List<WebElement> getHoursWorkedColumn() {
        return hoursWorkedColumn;
    }
}
