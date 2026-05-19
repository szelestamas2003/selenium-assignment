package szelestamas.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class LoadTest {
    public WebDriver webDriver;

    @BeforeEach
    public void setup() throws URISyntaxException, MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                /*"--headless=new",*/
                "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36",
                "--window-size=1920,1080");
        webDriver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), options);
    }

    @Test
    public void verifyPageTitle() {
        MainPage mainPage = new MainPage(this.webDriver);
        assertEquals("MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitle().toLowerCase());
    }

    @Test
    public void testLanguageChange() {
        MainPage mainPage = new MainPage(this.webDriver);
        List<String> options = mainPage.getNotSelectedLanguages();
        assertEquals("HU", mainPage.getSelectedLanguage());
        String newLanguage = options.get(new Random().nextInt(options.size()));
        mainPage.selectLanguage(newLanguage);
        assertEquals(newLanguage, mainPage.getSelectedLanguage());
        assertNotEquals("HU", mainPage.getSelectedLanguage());
    }

    @Test
    public void testSearchDestination() {
        MainPage mainPage = new MainPage(this.webDriver);
        mainPage.setDestination("Budapest-Kelenföld");
        assertTrue(mainPage.getDestination().contains("Budapest-Kelenföld"));
    }

    @Test
    public void testSearchOrigin() {
        MainPage mainPage = new MainPage(this.webDriver);
        mainPage.setOrigin("Nagykanizsa");
        assertTrue(mainPage.getOrigin().contains("Nagykanizsa"));
    }
}
