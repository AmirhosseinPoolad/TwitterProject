package main.java.org.ce.ap.client.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.server.jsonHandling.MapperSingleton;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectionServiceImpl implements ConnectionService {
    //server output stream
    OutputStream out;
    //server input stream
    InputStream in;
    //json object mapper for request/responses
    ObjectMapper mapper;
    //buffer for input stream
    byte[] buffer = new byte[1 << 20]; //1 megabyte buffer

    /**
     * constructs a connection service
     * @param out server output stream
     * @param in server input stream
     */
    public ConnectionServiceImpl(OutputStream out, InputStream in) {
        this.out = out;
        this.in = in;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
    }

    /**
     * sends request to server and returns response from server
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