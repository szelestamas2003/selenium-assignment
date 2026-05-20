package szelestamas.selenium;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TestDataProvider {
    @DataProvider(name = "dateProvider")
    public Object[][] getRandomDayLeftInCurrentMonth() {
        LocalDate today = LocalDate.now();
        int selectedDay;

        int currentDay = today.getDayOfMonth();
        int lastDayOfMonth = today.lengthOfMonth();

        if (currentDay >= lastDayOfMonth) {
            selectedDay = currentDay;
        }

        selectedDay = new Random().nextInt(currentDay, lastDayOfMonth + 1);
        String expectedDate;
        if (selectedDay == currentDay)
            expectedDate = "Ma";
        else if (selectedDay == currentDay + 1)
            expectedDate = "Holnap";
        else {
            LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), selectedDay);
            expectedDate = date.format(DateTimeFormatter.ofPattern("yyyy. MM. dd."));
        }
        return new Object[][] {
                { expectedDate, selectedDay }
        };
    }

    @DataProvider(name = "imageProvider")
    public Object[][] getImagePath() throws FileNotFoundException {
        URL resource =  TestDataProvider.class.getClassLoader().getResource(ConfigReader.getInstance().getProperty("image.name"));
        if (resource == null)
            throw new FileNotFoundException("File not found in resource folder!");

        String absolutePath = new File(resource.getFile()).getAbsolutePath();
        return new Object[][] {
                { absolutePath }
        };
    }

    @DataProvider(name = "goodCredentials")
    public Object[][] getGoodCredentials() {
        return new Object[][] {
                {
                        ConfigReader.getInstance().getProperty("login.good.email"),
                        ConfigReader.getInstance().getProperty("login.good.password")
                }
        };
    }

    @DataProvider(name = "LessThan8CharCreds")
    public Object[][] getLessThan8CharCredentials() {
        return new Object[][] {
                {
                    ConfigReader.getInstance().getProperty("login.bad.email"),
                    ConfigReader.getInstance().getProperty("login.bad.password1")
                }
        };
    }

    @DataProvider(name = "LowerCase8CharCreds")
    public Object[][] getLower8CharCredentials() {
        return new Object[][] {
                {
                        ConfigReader.getInstance().getProperty("login.bad.email"),
                        ConfigReader.getInstance().getProperty("login.bad.password2")
                }
        };
    }

    @DataProvider(name = "CamelCase8CharCreds")
    public Object[][] getCamel8CharCredentials() {
        return new Object[][] {
                {
                        ConfigReader.getInstance().getProperty("login.bad.email"),
                        ConfigReader.getInstance().getProperty("login.bad.password3")
                }
        };
    }

    @DataProvider(name = "CorrectlyStyledCreds")
    public Object[][] getCorrectlyStyledCredentials() {
        return new Object[][] {
                {
                        ConfigReader.getInstance().getProperty("login.bad.email"),
                        ConfigReader.getInstance().getProperty("login.bad.password4")
                }
        };
    }
}
