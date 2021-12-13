package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.entity.User;

import java.net.Socket;

public class Session implements Runnable {
    private User user;
    private Socket connectionSocket;

    public Session(Socket connectionSocket, User user) {
        this.connectionSocket = connectionSocket;
        this.user = user;
    }

    @Override
    public void run() {
        user.printInfo();
    }
}
