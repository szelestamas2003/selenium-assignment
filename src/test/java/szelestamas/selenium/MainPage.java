package szelestamas.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class MainPage extends BasePage {
    private By destinationLocator = By.id("endStation-input");
    private By startLocator = By.id("startStation-input");
    private String dropDownLocator = "//ng-dropdown-panel[@role='listbox' and @aria-label='választási lehetőségek']";
    private By destinationTextLocator = By.xpath("//app-select-search-dropdown[@name='endStation']//ng-select//div[contains(@class, 'ng-value-container')]");
    private By languageLocator = By.cssSelector("select[aria-label*='nyelv' i]");
    private By startTextLocator = By.xpath("//app-select-search-dropdown[@name='startStation']//ng-select//div[contains(@class, 'ng-value-container')]");

    public MainPage(WebDriver driver) {
        super(driver);
        this.webDriver.get("https://jegy.mav.hu");
        this.handleCookieConsent();
    }

    public void setDestination(String destination) {
        this.webDriver.get("https://jegy.mav.hu/bejelentkezes?compensationRequest=compensationRequest");
        this.webDriver.navigate().back();
        this.waitAndReturnElement(destinationLocator).sendKeys(destination);
        this.waitAndReturnNthDropdownElement(dropDownLocator, 1).click();
    }

    public String getDestination() {
        return this.waitAndReturnElement(destinationTextLocator).getAttribute("textContent");
    }

    public List<String> getNotSelectedLanguages() {
        Select languageSelect = new Select(this.waitAndReturnElement(languageLocator));
        return languageSelect.getOptions().stream().filter(option -> !languageSelect.getAllSelectedOptions().contains(option)).map(WebElement::getText).toList();
    }

    public void selectLanguage(String value) {
        Select languageSelect = new Select(this.waitAndReturnElement(languageLocator));
        languageSelect.selectByValue(value.toLowerCase());
    }

    public String getSelectedLanguage() {
        Select languageSelect = new Select(this.waitAndReturnElement(languageLocator));
        return languageSelect.getFirstSelectedOption().getText();
    }

    public void setOrigin(String origin) {
        this.waitAndReturnElement(startLocator).sendKeys(origin);
        this.waitAndReturnNthDropdownElement(dropDownLocator, 1).click();
    }

    public String getOrigin() {
        return this.waitAndReturnElement(startTextLocator).getAttribute("textContent");
    }
}
