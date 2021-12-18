package main.java.org.ce.ap.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        try (Socket client = new Socket("127.0.0.1", 7660)) {
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            Scanner sc = new Scanner(System.in);
            while (true) {
                String command = sc.nextLine();
                out.write(command.getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}