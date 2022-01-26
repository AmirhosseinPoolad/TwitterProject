package main.java.org.ce.ap.client.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.MapperSingleton;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.services.impl.AuthenticatorServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of ConnectionService for UI application that is a singleton, connects to server itself and holds the socket.
 */
public class UIConnectionService implements ConnectionService {
    //client socket
    Socket clientSocket;
    //server output stream
    OutputStream out;
    //server input stream
    InputStream in;
    //json object mapper for request/responses
    ObjectMapper mapper;
    //buffer for input stream
    byte[] buffer = new byte[1 << 20]; //1 megabyte buffer

    private static UIConnectionService INSTANCE = null;

    public static UIConnectionService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UIConnectionService();
        }
        return INSTANCE;
    }

    /**
     * conencts to the server and constructs a connection service
     */
    private UIConnectionService() {
        boolean isSuccessful = false;
        int count = 0;
        while (!isSuccessful && count < 10) {
            try {
                clientSocket = new Socket("127.0.0.1", Integer.parseInt(PropertiesServiceImpl.getInstance().getProperty("server.port")));
                out = clientSocket.getOutputStream();
                in = clientSocket.getInputStream();
                mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                isSuccessful = true;
            } catch (IOException e) {
                e.printStackTrace();
                count++;
            }
        }
    }

    /**
     * destroys the instance, allowing a new one to be constructed
     */
    public void destroyInstance() {
        try {
            clientSocket.close();
            INSTANCE = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends request to server and returns response from server
     *
     * @param request request to send
     * @return response from server
     */
    @Override
    public Response sendToServer(Request request) {
        try {
            String reqString = mapper.writeValueAsString(request);
            out.write(reqString.getBytes());
            String resString;
            int read = in.read(buffer);
            resString = new String(buffer, 0, read);
            Response req = MapperSingleton.getObjectMapper().readValue(resString, Response.class);
            return req;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}