package main.java.org.ce.ap.server.jsonHandling;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Response {
    @JsonProperty
    private boolean hasError;
    @JsonProperty
    private int errorCode;
    @JsonProperty
    private Result results;

    public Response(boolean hasError, int errorCode, Result results) {
        this.hasError = hasError;
        this.errorCode = errorCode;
        this.results = results;
    }
}
