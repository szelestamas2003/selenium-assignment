package szelestamas.selenium;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class BaseTest {
    protected RemoteWebDriver webDriver;

    @BeforeClass(alwaysRun = true)
    protected void setup() throws URISyntaxException, MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless=" + ConfigReader.getInstance().getProperty("selenium.options.headless"),
                "--user-agent=" + ConfigReader.getInstance().getProperty("selenium.options.user-agent"),
                "--window-size=" + ConfigReader.getInstance().getProperty("selenium.options.window-size"));
        webDriver = new RemoteWebDriver(new URI(ConfigReader.getInstance().getProperty("selenium.url")).toURL(), options);
        webDriver.get(ConfigReader.getInstance().getProperty("base.url"));
    }
}
