package main.java.org.ce.ap.client.services;

import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;

public interface ConnectionService {
    /**
     * sends request to server and returns response from server
     * @param request request to send
     * @return response from server
     */
    Response sendToServer(Request request);
}
