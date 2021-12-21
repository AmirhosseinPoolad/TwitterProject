package main.java.org.ce.ap.client.services;

import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.server.jsonHandling.Response;

public interface ConsoleViewService {
    void showResponse(MenuStatus status, Response response);
}
