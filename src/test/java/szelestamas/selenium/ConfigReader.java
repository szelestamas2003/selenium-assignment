package szelestamas.selenium;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static ConfigReader instance;

    private ConfigReader() {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null)
            instance = new ConfigReader();
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
