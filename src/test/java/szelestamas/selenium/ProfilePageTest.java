package szelestamas.selenium;

import org.openqa.selenium.remote.LocalFileDetector;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfilePageTest extends BaseTest {
    public ProfilePage profilePage;

    @BeforeClass(alwaysRun = true)
    public void setup() throws URISyntaxException, MalformedURLException {
        super.setup();
        this.webDriver.setFileDetector(new LocalFileDetector());
        profilePage = new ProfilePage(this.webDriver);
        profilePage.handleCookieConsent();
        LoginPage loginPage = profilePage.clickLoginMenu();
        loginPage.setEmail(ConfigReader.getInstance().getProperty("login.good.email"));
        loginPage.setPassword(ConfigReader.getInstance().getProperty("login.good.password"));
        loginPage.clickLogin();
        profilePage.clickProfileMenu();
    }

    @Test
    public void testChangingName() {
        profilePage.setFirstName("Teszt");
        assertEquals("Teszt", profilePage.getFirstName());
        profilePage.setLastName("Péter");
        assertEquals("Péter", profilePage.getLastName());
        profilePage.clickSaveNameChange();
        assertEquals("Profiladatait sikeresen frissítette.", this.profilePage.getSuccessMessage());
        profilePage.closeSuccessMessage();
    }

    @Test(dataProvider = "imageProvider", dataProviderClass = TestDataProvider.class)
    public void uploadProfilePicture(String imagePath) {
        profilePage.uploadProfilePicture(imagePath);
        assertEquals("Profiladatok sikeresen frissítve lettek", this.profilePage.getSuccessMessage());
        profilePage.closeSuccessMessage();
    }

    @AfterClass
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
