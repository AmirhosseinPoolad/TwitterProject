package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.services.CommandParser;
import main.java.org.ce.ap.client.services.impl.CommandParserImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        //TODO: get port and ip from properties
        try (Socket client = new Socket("127.0.0.1", 8080)) {
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