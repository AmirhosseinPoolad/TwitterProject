package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.services.CommandParser;
import main.java.org.ce.ap.client.services.impl.CommandParserImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        Properties properties;
        try (InputStream in = new FileInputStream("src/main/resources/client-application.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (Socket client = new Socket("127.0.0.1", Integer.parseInt(properties.getProperty("server.port")))) {
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            CommandParser parser = new CommandParserImpl(out, in);
            int ret = 0;
            while (ret == 0) {
                ret = parser.handleInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}