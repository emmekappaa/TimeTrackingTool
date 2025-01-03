package demo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject{

    // Costruttore
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Elementi della pagina
    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(css = ".login-btn")
    private WebElement loginButton;

    @FindBy(css = ".error-message p")
    private WebElement errorMessage;

    // Metodi di interazione

    /**
     * Inserisce il nome utente nel campo username.
     * @param username Il nome utente da inserire.
     */
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    /**
     * Inserisce la password nel campo password.
     * @param password La password da inserire.
     */
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Clicca sul pulsante di login.
     */
    public void clickLoginButton() {
        loginButton.click();
    }

    /**
     * Ottiene il messaggio di errore visualizzato.
     * @return Il messaggio di errore come stringa.
     */
    public String getErrorMessage() {
        return errorMessage.getText();
    }

    /**
     * Esegue il login con nome utente e password specificati.
     * @param username Il nome utente.
     * @param password La password.
     */
    public HomeResearcherPageObject loginResearcher(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new HomeResearcherPageObject(driver);
    }

    public HomeManagerPageObject loginManager(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new HomeManagerPageObject(driver);
    }
}
