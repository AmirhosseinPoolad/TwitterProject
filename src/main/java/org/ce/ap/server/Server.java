package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.impl.AuthenticatorServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        AuthenticatorServiceImpl authenticatorService = AuthenticatorServiceImpl.getInstance();
        /*authenticatorService.signUp("Brrrr", "12345", "Amir",
                "Boolad", "Not So Random dude", LocalDate.of(2000, 11, 10));*/
        /*authenticatorService.signUp("HDxC", "1234", "Amirhossein",
                "Poolad", "Random dude", LocalDate.of(2000, 10, 11));*/
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket welcomingSocket = new ServerSocket(7660)) {
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                InputStream in = connectionSocket.getInputStream();
                byte[] buffer = new byte[2048];
                String username, password;
                int read = in.read(buffer);
                username = new String(buffer, 0, read);
                read = in.read(buffer);
                password = new String(buffer, 0, read);
                User user;
                if (authenticatorService.userExists(username)) {
                    if ((user = authenticatorService.logIn(username, password)) != null) {
                        pool.execute(new Session(connectionSocket, user));
                    }
                }
                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
