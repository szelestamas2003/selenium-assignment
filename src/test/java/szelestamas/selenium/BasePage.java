package szelestamas.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver webDriver;
    protected WebDriverWait wait;
    private String cookieTemplate = """
            {"allowTrackingCookie": false, "timeStamp": %d}""";

    public BasePage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public BasePage(WebDriver driver, int duration) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
    }

    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.webDriver.findElement(locator);
    }

    protected WebElement waitAndReturnNthDropdownElement(String locatorString, int n) {
        By locator = By.xpath(String.format("%s//div[@role='option'][%d]", locatorString, n));
        this.wait.until(ExpectedConditions.elementToBeClickable(locator));
        return this.webDriver.findElement(locator);
    }

    protected String getTitle() {
        return this.webDriver.getTitle();
    }

    protected void handleCookieConsent() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        long currentTime = System.currentTimeMillis();
        System.out.println(String.format(cookieTemplate, currentTime));
        js.executeScript(String.format("window.localStorage.setItem('cookieSettings', '%s')", String.format(cookieTemplate, currentTime)));
    }
}
