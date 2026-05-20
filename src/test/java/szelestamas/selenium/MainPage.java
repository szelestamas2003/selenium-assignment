package szelestamas.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MainPage extends BasePage {
    private final By destinationLocator = By.id("endStation-input");
    private final By startLocator = By.id("startStation-input");
    private final String dropDownLocator = "//ng-dropdown-panel[@role='listbox' and contains(@class, 'ng-dropdown-panel')]";
    private final By searchButton = By.xpath("//div[contains(@class, 'search-button-row')]//button[@type='submit']");
    private final By airconLocator = By.id("onlyAirConditioned");
    private final By departureTimeLocator = By.id("isTimeDeparture");
    private final By startTimeLocator = By.id("travelStartTime-input");
    private final By datePickerLocator = By.xpath("//app-relative-datepicker[@formcontrolname='travelStartDate']//button[contains(@class, 'datepicker-toggler-button')]");
    private final String dateSelectorLocator = "//app-datepicker//button[contains(@class, 'dateButton') and not(contains(@class, 'hidden')) and normalize-space()='%d']";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void setDestination(String destination) {
        WebElement element = this.waitAndReturnElement(destinationLocator);
        element.clear();
        element.sendKeys(destination);
        this.waitAndReturnNthDropdownElement(dropDownLocator, 1).click();
    }

    public String getDestination() {
        return this.waitAndReturnElement(destinationLocator).getAttribute("value");
    }

    public void setOrigin(String origin) {
        WebElement element = this.waitAndReturnElement(startLocator);
        element.clear();
        element.sendKeys(origin);
        this.waitAndReturnNthDropdownElement(dropDownLocator, 1).click();
    }

    public void clickSearch() {
        this.waitAndReturnElement(searchButton).click();
    }

    public String getOrigin() {
        return this.waitAndReturnElement(startLocator).getAttribute("value");
    }

    public String getTitleAfterSearch() {
        return this.getTitle("/jegy/vonat");
    }

    public String getTitleOnMainPage() {
        return this.getTitle("");
    }

    public void toggleAirConditioned() {
        this.waitAndReturnElement(airconLocator).click();
    }

    public boolean isAirConditionedSelected() {
        return this.waitAndReturnElement(airconLocator).isSelected();
    }

    public boolean isDepartureTimeSelected() {
        return this.waitAndReturnElement(departureTimeLocator).isSelected();
    }

    public void toggleDepartureTimeRadio() {
        this.waitAndReturnElement(departureTimeLocator).click();
    }

    public void setStartTime(String time) {
        WebElement element = this.waitAndReturnElement(startTimeLocator);
        element.clear();
        element.sendKeys(time);
        this.waitAndReturnNthDropdownElement(dropDownLocator, 1).click();
    }

    public String getStartTime() {
        return this.waitAndReturnElement(startTimeLocator).getAttribute("value");
    }

    public void setDateInCurrentMonth(int date) {
        this.waitAndReturnElement(datePickerLocator).click();
        this.waitAndReturnElement(By.xpath(String.format(dateSelectorLocator, date))).click();
    }

    public String getDate() {
        return this.waitAndReturnElement(datePickerLocator).getText();
    }

    public void hoverOverDropdownOption(int n) {
        WebElement element = this.waitAndReturnNthDropdownElement(dropDownLocator, n);
        new Actions(this.webDriver).moveToElement(element).perform();
    }

    public void backspaceAtOrigin() {
        this.waitAndReturnElement(startLocator).sendKeys(Keys.BACK_SPACE);
    }

    public boolean NthDropdownElementIsSelected(int n) {
        return this.waitAndReturnNthDropdownElement(dropDownLocator, n).getAttribute("class").contains("ng-option-selected");
    }

    public boolean NthDropdownElementIsMarked(int n) {
        return this.waitAndReturnNthDropdownElement(dropDownLocator, n).getAttribute("class").contains("ng-option-marked");
    }
}
