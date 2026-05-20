package szelestamas.selenium;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest extends BaseTest {
    public MainPage mainPage;

    @BeforeClass(alwaysRun = true)
    public void setup() throws URISyntaxException, MalformedURLException {
        super.setup();
        mainPage = new MainPage(this.webDriver);
        mainPage.handleCookieConsent();
    }

    @Test
    public void testLanguageChange() {
        List<String> options = mainPage.getNotSelectedLanguages();
        assertEquals("HU", mainPage.getSelectedLanguage());
        String newLanguage = options.get(new Random().nextInt(options.size()));
        mainPage.selectLanguage(newLanguage);
        assertEquals(newLanguage, mainPage.getSelectedLanguage());
        assertNotEquals("HU", mainPage.getSelectedLanguage());
        mainPage.selectLanguage("HU");
    }

    @Test
    public void testSearchDestination() {
        mainPage.setDestination("Budapest-Kelenföld");
        assertEquals("Budapest-Kelenföld", mainPage.getDestination());
    }

    @Test
    public void testSearchOrigin() {
        mainPage.setOrigin("Nagykanizsa");
        assertEquals("Nagykanizsa", mainPage.getOrigin());
    }

    @Test
    public void testBasicSearch() {
        mainPage.setOrigin("Nagykanizsa");
        assertEquals("Nagykanizsa", mainPage.getOrigin());
        mainPage.setDestination("Budapest-Kelenföld");
        assertEquals("Budapest-Kelenföld", mainPage.getDestination());
        mainPage.clickSearch();
        assertNotEquals("MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitleAfterSearch().toLowerCase());
        assertEquals("Jegyvásárlás | MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitleAfterSearch().toLowerCase());
        this.webDriver.navigate().back();
        assertEquals("MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitleOnMainPage().toLowerCase());
    }

    @Test(dataProvider = "dateProvider", dataProviderClass = TestDataProvider.class)
    public void testComplexSearch(String expectedDate, int date) {
        mainPage.setOrigin("Nagykanizsa");
        assertEquals("Nagykanizsa", mainPage.getOrigin());
        mainPage.setDestination("Budapest-Kelenföld");
        assertEquals("Budapest-Kelenföld", mainPage.getDestination());
        mainPage.toggleAirConditioned();
        assertTrue(mainPage.isAirConditionedSelected());
        if (!mainPage.isDepartureTimeSelected())
            mainPage.toggleDepartureTimeRadio();
        assertTrue(mainPage.isDepartureTimeSelected());
        mainPage.setStartTime("0900");
        assertEquals("09:00", mainPage.getStartTime());
        mainPage.setDateInCurrentMonth(date);
        assertEquals(expectedDate, mainPage.getDate());
        mainPage.clickSearch();
        assertEquals("Jegyvásárlás | MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitleAfterSearch().toLowerCase());
        this.webDriver.navigate().back();
        assertEquals("MÁV Személyszállítási Zrt. új ELVIRA online jegyvásárlás".toLowerCase(), mainPage.getTitleOnMainPage().toLowerCase());
    }

    @Test
    public void testMouseHover() {
        mainPage.setOrigin("Budapest*");
        mainPage.backspaceAtOrigin();
        mainPage.hoverOverDropdownOption(4);
        assertTrue(mainPage.NthDropdownElementIsSelected(1));
        assertFalse(mainPage.NthDropdownElementIsMarked(1));
        assertTrue(mainPage.NthDropdownElementIsMarked(4));
        assertFalse(mainPage.NthDropdownElementIsSelected(4));
    }

    @AfterClass
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
