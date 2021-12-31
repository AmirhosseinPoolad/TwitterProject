package main.java.org.ce.ap.client.services;

public interface CommandParser {
    /**
     * handles input and controls the client
     * @return 1 if we quit the program
     */
    int handleInput();
}
