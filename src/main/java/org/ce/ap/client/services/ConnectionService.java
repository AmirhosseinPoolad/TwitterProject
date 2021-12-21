package main.java.org.ce.ap.client.services;

import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;

public interface ConnectionService {
    Response sendToServer(Request request);
}
