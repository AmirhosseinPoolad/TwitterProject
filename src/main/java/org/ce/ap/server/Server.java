package main.java.org.ce.ap.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.TweetGraph;
import main.java.org.ce.ap.server.services.impl.AuthenticatorServiceImpl;
import main.java.org.ce.ap.server.jsonHandling.MapperSingleton;
import main.java.org.ce.ap.server.services.impl.PropertyServiceImpl;
import main.java.org.ce.ap.server.util.Tree;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        AuthenticatorServiceImpl authenticatorService = AuthenticatorServiceImpl.getInstance();
        /*authenticatorService.signUp("Brrrr", "12345", "Amir",
                "Boolad", "Not So Random dude", LocalDate.of(2000, 11, 10));
        authenticatorService.signUp("HDxC", "1234", "Amirhossein",
                "Poolad", "Random dude", LocalDate.of(2000, 10, 11));*/
        //amirhdxc 5678
        ObjectMapper objectMapper = MapperSingleton.getObjectMapper();
        /*Tweet t1 = new Tweet("HDxC", "Hello World", 0);
        Tweet t2 = new Tweet("Brrrr", "Goodbye World", 1);
        TweetGraph.getInstance().addTweet(t1, (Tree<Tweet>) null);
        TweetGraph.getInstance().addTweet(t2, (Tree<Tweet>) null);*/
        TweetGraph graph = TweetGraph.getInstance();
        PropertyServiceImpl propertyService = PropertyServiceImpl.getInstance();
        int port = Integer.parseInt(propertyService.getProperty("server.port"));
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket welcomingSocket = new ServerSocket(port)) {
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
