package main.java.org.ce.ap.server.jsonHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * singleton used for saving and reusing json object mapper
 */
public class MapperSingleton {
    private ObjectMapper objectMapper;
    private static MapperSingleton INSTANCE;

    private MapperSingleton() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static synchronized ObjectMapper getObjectMapper() {
        if (INSTANCE == null) {
            INSTANCE = new MapperSingleton();
        }
        return INSTANCE.objectMapper;
    }
}
