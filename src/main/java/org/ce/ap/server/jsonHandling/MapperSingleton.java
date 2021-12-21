package main.java.org.ce.ap.server.jsonHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MapperSingleton {
    private ObjectMapper objectMapper;
    private static MapperSingleton INSTANCE;

    private MapperSingleton() {
        objectMapper = new ObjectMapper();
        /*PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("main.java.org.ce.ap.server.jsonHandling.Parameter")
                .allowIfSubType("main.java.org.ce.ap.server.jsonHandling.Result")
                .allowIfSubType("java.util.ArrayList")
                .build();*/
        objectMapper.registerModule(new JavaTimeModule());
        //objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public static synchronized ObjectMapper getObjectMapper() {
        if (INSTANCE == null) {
            INSTANCE = new MapperSingleton();
        }
        return INSTANCE.objectMapper;
    }
}
