package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='error-message']")
    private WebElement errorMessage;


    public void enterCredentials(String username, String password) {
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /*
    public HomePage submitLogin() {
        loginButton.click();
        return new HomePage(driver);  // Assuming you want to navigate to a HomePage after login
    }
    */
    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
