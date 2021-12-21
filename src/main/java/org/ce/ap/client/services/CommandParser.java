package main.java.org.ce.ap.client.services;

import main.java.org.ce.ap.server.jsonHandling.Request;

public interface CommandParser {
    int handleInput();

    Request parseString(String input);
}
