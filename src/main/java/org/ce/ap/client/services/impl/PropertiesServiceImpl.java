package main.java.org.ce.ap.client.services.impl;

import main.java.org.ce.ap.client.services.PropertiesService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesServiceImpl implements PropertiesService {

    Properties properties;

    private static PropertiesService INSTANCE = null;

    public static PropertiesService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PropertiesServiceImpl();
        }
        return INSTANCE;
    }

    private PropertiesServiceImpl() {
        try (InputStream in = new FileInputStream("src/main/resources/client-application.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets property of key=name from properties file
     *
     * @param key key of property
     * @return value of key
     */
    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * sets property of key from properties file to value
     *
     * @param key   key of property
     * @param value value of property
     */
    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream("src/main/resources/client-application.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
