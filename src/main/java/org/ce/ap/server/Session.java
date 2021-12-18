package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.impl.AuthenticatorServiceImpl;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.server.services.TimelineService;
import main.java.org.ce.ap.server.services.TweetingService;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

public class Session implements Runnable {
    private User user = null;
    private Socket connectionSocket;
    private TimelineService timelineService;
    private TweetingService tweetingService;

    public Session(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    private int signIn(String username, String password) {
        this.user = AuthenticatorServiceImpl.getInstance().logIn(username, password);
        if (user != null) {
            this.timelineService = new TimelineServiceImpl(user);
            this.tweetingService = new TweetingServiceImpl(user);
            return 1;
        }
        return 0;
    }

    @Override
    public void run() {
        try {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[4096];
            String readString;
            String command;
            do {
                int read = in.read(buffer);
                readString = new String(buffer, 0, read);
                String[] inputList = readString.split(" ");
                command = inputList[0];
                if (inputList[0].equals("-RegisterUser")) {
                    AuthenticatorServiceImpl.getInstance().signUp(inputList[1], inputList[2], inputList[3],
                            inputList[4], inputList[5], LocalDate.parse(inputList[6]));
                    System.out.println("REGISTERED");
                } else if (inputList[0].equals("-SignIn")) {
                    if (signIn(inputList[1], inputList[2]) == 1) {
                        System.out.println("SIGNED IN");
                        signedInRun();
                    }
                }
            } while (!(command.equals("-ClientExit")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signedInRun() {

    }
}
