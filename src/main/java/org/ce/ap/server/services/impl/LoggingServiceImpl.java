package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.services.LoggingService;

import java.io.*;

public class LoggingServiceImpl implements LoggingService {
    private String path;
    private static LoggingServiceImpl INSTANCE = null;

    public static synchronized LoggingServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoggingServiceImpl();
        }
        return INSTANCE;
    }

    private LoggingServiceImpl() {
        path = PropertyServiceImpl.getInstance().getProperty("server.log.file");
    }

    @Override
    public void log(String logString) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path, true))) {
            out.write(logString);
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
