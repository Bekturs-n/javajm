package com.javamentor.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream in = PropertyReader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(in);
        } catch (IOException e) {
            properties = null;
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return properties;
    }
}
