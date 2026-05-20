package szelestamas.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver webDriver;
    protected WebDriverWait wait;
    protected final By languageLocator = By.cssSelector("select[aria-label*='nyelv' i]");
    protected final By profileLocator = By.id("profile-popup-button");
    protected final By profileMenuLocator = By.id("profile-button");
    protected final By logoutLocator = By.id("logout");
    protected final By loginLocator = By.id("login");
    protected final By logoutConfirmationLocator = By.xpath("//div[@id = 'popup-container']//div[contains(@class, 'popup-buttons')]/button[@type='button']");
    protected final By infoButtonLocator = By.xpath("//app-snackbar[contains(@class, 'info')]//button");
    protected final By infoLocator = By.xpath("//app-snackbar[contains(@class, 'info')]//span[@role='status']");
    protected final By profileDropdownLocator = By.xpath("//app-header-profile/app-profile-dropdown");

    public BasePage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.selectLanguage("hu");
    }

    public BasePage(WebDriver driver, int duration) {
        this.webDriver = driver;
        this.handleCookieConsent();
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

    protected String getTitle(String urlFragment) {
        this.wait.until(ExpectedConditions.urlContains(urlFragment));
        return this.webDriver.getTitle();
    }

    public void handleCookieConsent() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        long currentTime = System.currentTimeMillis();
        String cookieTemplate = """
                {"allowTrackingCookie": false, "timeStamp": %d}""";
        js.executeScript(String.format("window.localStorage.setItem('cookieSettings', '%s')", String.format(cookieTemplate, currentTime)));
        this.webDriver.navigate().refresh();
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

    public LoginPage clickLoginMenu() {
        try {
            this.waitAndReturnElement(infoButtonLocator).click();
        } catch (TimeoutException ignored) {}
        this.waitAndReturnElement(profileLocator).click();
        this.waitAndReturnElement(loginLocator).click();
        this.getTitle("bejelentkezes");
        return new LoginPage(this.webDriver);
    }

    public ProfilePage clickProfileMenu() {
        try {
            this.waitAndReturnElement(infoButtonLocator).click();
        } catch (TimeoutException ignored) {}
        this.waitAndReturnElement(profileLocator).click();
        this.waitAndReturnElement(profileMenuLocator).click();
        return new ProfilePage(this.webDriver);
    }

    public boolean isProfileMenuVisible() {
        try {
            this.waitAndReturnElement(infoButtonLocator).click();
        } catch (TimeoutException ignored) {}
        try {
            this.waitAndReturnElement(profileDropdownLocator);
        } catch (Exception ignored) {
            this.waitAndReturnElement(profileLocator).click();
        }
        try {
            return this.waitAndReturnElement(profileMenuLocator).isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }

    public String getInfoMessage() {
        return this.waitAndReturnElement(infoLocator).getText();
    }

    public boolean isLogoutMenuVisible() {
        try {
            this.waitAndReturnElement(infoButtonLocator).click();
        } catch (TimeoutException ignored) {}
        try {
            this.waitAndReturnElement(profileDropdownLocator);
        } catch (Exception ignored) {
            this.waitAndReturnElement(profileLocator).click();
        }
        try {
            return this.waitAndReturnElement(logoutLocator).isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isLoginMenuVisible() {
        try {
            this.waitAndReturnElement(infoButtonLocator).click();
        } catch (TimeoutException ignored) {}
        try {
            this.waitAndReturnElement(profileDropdownLocator);
        } catch (Exception ignored) {
            this.waitAndReturnElement(profileLocator).click();
        }
        try {
            return this.waitAndReturnElement(loginLocator).isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }
}
