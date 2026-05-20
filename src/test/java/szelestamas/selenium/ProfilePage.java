package szelestamas.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {
    private final By lastNameLocator = By.id("lastNameFie");
    private final By firstNameLocator = By.id("firstNameFie");
    private final By saveChangeLocator = By.id("personal-info-save-button");
    private final By successMessageLocator = By.xpath("//app-snackbar[contains(@class, 'success')]//span[@role='status']");
    private final By successButtonLocator = By.xpath("//app-snackbar[contains(@class, 'success')]//button");
    private final By profileInputLocator = By.xpath("//form[@id='personal-info-form']//div[@id='img-upload']/button[@id='add-picture.button']/input[@type='file' and contains(@class, 'img-upload-input')]");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public void setLastName(String lastName) {
        WebElement element = this.waitAndReturnElement(lastNameLocator);
        element.clear();
        element.sendKeys(lastName);
    }

    public String getLastName() {
        return this.waitAndReturnElement(lastNameLocator).getAttribute("value");
    }

    public void setFirstName(String firstName) {
        WebElement element = this.waitAndReturnElement(firstNameLocator);
        element.clear();
        element.sendKeys(firstName);
    }

    public String getFirstName() {
        return this.waitAndReturnElement(firstNameLocator).getAttribute("value");
    }

    public void clickSaveNameChange() {
        this.waitAndReturnElement(saveChangeLocator).click();
    }

    public String getSuccessMessage() {
        return this.waitAndReturnElement(successMessageLocator).getText();
    }

    public void closeSuccessMessage() {
        this.waitAndReturnElement(successButtonLocator).click();
    }

    public void uploadProfilePicture(String fileName) {
        this.wait.until(ExpectedConditions.presenceOfElementLocated(profileInputLocator)).sendKeys(fileName);
    }
}
