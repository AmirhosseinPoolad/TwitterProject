package main.java.org.ce.ap.server.services;

public interface PropertiesService {
    /**
     * gets property of key=name from properties file
     * @param name key of property
     * @return value of key
     */
    String getProperty(String name);
}
