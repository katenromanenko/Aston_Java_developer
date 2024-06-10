package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class propertiesFile {
    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE = "database.properties";

    static {
        loadProperties();
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inFile = propertiesFile.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (inFile != null) {
                PROPERTIES.load(inFile);
            } else {
                throw new IllegalArgumentException("Файл свойств " + PROPERTIES_FILE + " не найден.");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка при чтении файла свойств: " + PROPERTIES_FILE, e);
        }
    }
}