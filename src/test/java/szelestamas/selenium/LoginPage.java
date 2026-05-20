package szelestamas.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By emailLocator = By.id("emailFie");
    private final By passwordLocator = By.id("passwordFie");
    private final By loginLocator = By.id("login-btn");
    private final By credentialsErrorLocator = By.id("credentialErrorLabel");
    private final By passwordErrorLocator = By.id("passwordErrorLabel");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setEmail(String email) {
        WebElement element = this.waitAndReturnElement(emailLocator);
        element.clear();
        element.sendKeys(email);
    }

    public String getEmail() {
        return this.waitAndReturnElement(emailLocator).getAttribute("value");
    }

    public void setPassword(String password) {
        WebElement element = this.waitAndReturnElement(passwordLocator);
        element.clear();
        element.sendKeys(password);
    }

    public void clickLogin() {
        this.waitAndReturnElement(loginLocator).click();
    }

    public MainPage clickLogout() {
        try {
            this.waitAndReturnElement(infoButtonLocator).click();
        } catch (TimeoutException ignored) {}
        try {
            this.waitAndReturnElement(profileDropdownLocator);
        } catch (Exception ignored) {
            this.waitAndReturnElement(profileLocator).click();
        }
        this.waitAndReturnElement(logoutLocator).click();
        this.waitAndReturnElement(logoutConfirmationLocator).click();
        return new MainPage(this.webDriver);
    }

    public String getCredentialsErrorText() {
        return this.waitAndReturnElement(credentialsErrorLocator).getText();
    }

    public String getPasswordErrorText() {
        return this.waitAndReturnElement(passwordErrorLocator).getText();
    }
}
