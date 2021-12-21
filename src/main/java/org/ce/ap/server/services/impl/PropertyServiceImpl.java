package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.services.PropertiesService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyServiceImpl implements PropertiesService {

    Properties properties;

    private static PropertyServiceImpl INSTANCE = null;

    public static synchronized PropertyServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PropertyServiceImpl();
        }
        return INSTANCE;
    }

    private PropertyServiceImpl() {
        try (InputStream in = new FileInputStream("src/main/resources/server-application.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
