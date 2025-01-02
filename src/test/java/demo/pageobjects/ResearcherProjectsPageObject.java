package demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ResearcherProjectsPageObject extends PageObject {

    // Costruttore
    public ResearcherProjectsPageObject(WebDriver driver) {
        super(driver);
    }

    // Elementi della pagina

    @FindBy(id = "back-link")
    private WebElement backLink;

    @FindBy(css = "table tbody tr")
    private List<WebElement> activeProjectRows;

    @FindBy(css = "h3:contains('Active Projects')")
    private WebElement activeProjectsHeader;

    @FindBy(css = "h3:contains('Pending Projects')")
    private WebElement pendingProjectsHeader;

    @FindBy(css = "table tbody tr")
    private List<WebElement> pendingProjectRows;

    // Metodi di interazione

    /**
     * Torna alla home del ricercatore.
     */
    public void clickBackLink() {
        backLink.click();
    }

    /**
     * Ottieni i nomi dei progetti attivi.
     * @return Lista dei nomi dei progetti attivi.
     */
    public List<String> getActiveProjectNames() {
        return activeProjectRows.stream()
                .map(row -> row.getText().trim())
                .collect(Collectors.toList());
    }


    public boolean arePendingProjectThere() {

        List<WebElement> noProjectsMessage = driver.findElements(By.xpath("//*[text()='No pending projects at the moment.']"));
        if (noProjectsMessage.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Clicca sul pulsante per accettare un progetto pendente.
     * @param projectName Nome del progetto da accettare.
     */
    public void acceptPendingProject(String projectName) {
        WebElement row = pendingProjectRows.stream()
                .filter(r -> r.findElement(By.xpath(".//td[1]")).getText().trim().equals(projectName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectName));
        WebElement acceptButton = row.findElement(By.cssSelector(".accept-btn"));
        acceptButton.click();
    }

    /**
     * Clicca sul pulsante per rifiutare un progetto pendente.
     * @param projectName Nome del progetto da rifiutare.
     */
    public void declinePendingProject(String projectName) {
        WebElement row = pendingProjectRows.stream()
                .filter(r -> r.findElement(By.xpath(".//td[1]")).getText().trim().equals(projectName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectName));
        WebElement declineButton = row.findElement(By.cssSelector(".decline-btn"));
        declineButton.click();
    }

    /**
     * Verifica se un progetto attivo esiste nella lista dei progetti attivi.
     * @param projectName Nome del progetto.
     * @return true se il progetto è attivo, false altrimenti.
     */
    public boolean isActiveProjectPresent(String projectName) {
        return activeProjectRows.stream()
                .anyMatch(row -> row.getText().trim().equals(projectName));
    }

    /**
     * Verifica se un progetto pendente esiste nella lista dei progetti pendenti.
     * @param projectName Nome del progetto.
     * @return true se il progetto è pendente, false altrimenti.
     */
    public boolean isPendingProjectPresent(String projectName) {
        return pendingProjectRows.stream()
                .anyMatch(row -> row.findElement(By.xpath(".//td[1]")).getText().trim().equals(projectName));
    }
}
