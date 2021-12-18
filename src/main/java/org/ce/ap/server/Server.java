package main.java.org.ce.ap.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import main.java.org.ce.ap.server.entity.TweetGraph;
import main.java.org.ce.ap.server.impl.AuthenticatorServiceImpl;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        AuthenticatorServiceImpl authenticatorService = AuthenticatorServiceImpl.getInstance();
        /*authenticatorService.signUp("Brrrr", "12345", "Amir",
                "Boolad", "Not So Random dude", LocalDate.of(2000, 11, 10));*/
        /*authenticatorService.signUp("HDxC", "1234", "Amirhossein",
                "Poolad", "Random dude", LocalDate.of(2000, 10, 11));*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(new File("test.json"),TweetGraph.getInstance().getTweetTree());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket welcomingSocket = new ServerSocket(7660)) {
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                pool.execute(new Session(connectionSocket));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
