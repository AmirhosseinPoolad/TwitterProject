package main.java.org.ce.ap.client.services;

public interface PropertiesService {
    /**
     * gets property of key=name from properties file
     * @param name key of property
     * @return value of key
     */
    public String getProperty(String key);

    public void setProperty(String key, String value);
}
