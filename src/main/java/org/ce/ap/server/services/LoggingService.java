package main.java.org.ce.ap.server.services;

public interface LoggingService {
    /**
     * writes logstring to log file specified in properties
     * @param logString string to be written
     */
    public  void log(String logString);
}
