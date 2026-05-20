package szelestamas.selenium;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest extends BaseTest {
    public LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void setup() throws URISyntaxException, MalformedURLException {
        super.setup();
        loginPage = new LoginPage(this.webDriver);
        loginPage.handleCookieConsent();
        loginPage.clickLoginMenu();
    }

    @Test(dataProvider = "LessThan8CharCreds", dataProviderClass = TestDataProvider.class, groups = {"passwordTesting"})
    public void testLoginWithPasswordLessThan8Chars(String email, String password) {
        loginPage.setEmail(email);
        assertEquals(email, loginPage.getEmail());
        loginPage.setPassword(password);
        loginPage.clickLogin();
        assertEquals("A jelszó mező értékének legalább 8 karakter hosszúnak kell lennie.".toLowerCase(), loginPage.getPasswordErrorText().toLowerCase());
    }

    @Test(dependsOnMethods = {"testLoginWithPasswordLessThan8Chars"}, dataProvider = "LowerCase8CharCreds", dataProviderClass = TestDataProvider.class, groups = {"passwordTesting"})
    public void testLoginWithPasswordLowerCase8Chars(String email, String password) {
        loginPage.setEmail(email);
        assertEquals(email, loginPage.getEmail());
        loginPage.setPassword(password);
        loginPage.clickLogin();
        assertEquals("A jelszó mező értékének legalább egy nagybetűt (A-Z) kell tartalmaznia.".toLowerCase(), loginPage.getPasswordErrorText().toLowerCase());
    }

    @Test(dependsOnMethods = {"testLoginWithPasswordLowerCase8Chars"}, dataProvider = "CamelCase8CharCreds", dataProviderClass = TestDataProvider.class, groups = {"passwordTesting"})
    public void testLoginWithPasswordCamel8Chars(String email, String password) {
        loginPage.setEmail(email);
        assertEquals(email, loginPage.getEmail());
        loginPage.setPassword(password);
        loginPage.clickLogin();
        assertEquals("A jelszó mező értékének legalább egy számot (0-9) kell tartalmaznia.".toLowerCase(), loginPage.getPasswordErrorText().toLowerCase());
    }

    @Test(dependsOnMethods = {"testLoginWithPasswordCamel8Chars"}, dataProvider = "CorrectlyStyledCreds", dataProviderClass = TestDataProvider.class, groups = {"passwordTesting"})
    public void testLoginWithWrongCredentials(String email, String password) {
        loginPage.setEmail(email);
        assertEquals(email, loginPage.getEmail());
        loginPage.setPassword(password);
        loginPage.clickLogin();
        assertEquals("Hibás e-mail cím vagy jelszó".toLowerCase(), loginPage.getCredentialsErrorText().toLowerCase());
    }

    @Test(dataProvider = "goodCredentials", dataProviderClass = TestDataProvider.class, groups = {"login"}, dependsOnGroups = {"passwordTesting"})
    public void testLoginWithCorrectCredentials(String email, String password) {
        loginPage.setEmail(email);
        assertEquals(email, loginPage.getEmail());
        loginPage.setPassword(password);
        loginPage.clickLogin();
        MainPage mainPage = new MainPage(this.webDriver);
        assertFalse(mainPage.isLoginMenuVisible());
        assertTrue(mainPage.isLogoutMenuVisible());
        assertTrue(mainPage.isProfileMenuVisible());
        assertEquals("MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitleOnMainPage().toLowerCase());
    }

    @Test(dependsOnGroups = {"login"})
    public void testLogout() {
        MainPage mainPage = loginPage.clickLogout();
        assertEquals("Sikeresen kijelentkezett", mainPage.getInfoMessage());
        assertTrue(mainPage.isLoginMenuVisible());
        assertFalse(mainPage.isLogoutMenuVisible());
        assertFalse(mainPage.isProfileMenuVisible());
        assertEquals("MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitleOnMainPage().toLowerCase());
    }

    @AfterClass
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
