package hust.soict.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";

    public static void saveSetting(String key, String value) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        properties.setProperty(key, value);
        
        try (FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE)) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    public static String getSetting(String key) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            // File doesn't exist, ignore or handle the exception as needed
        }
        
        return properties.getProperty(key);
    }
}

