package main.java.org.ce.ap.server.jsonHandling;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * server response object
 */
public class Response {
    @JsonProperty
    //if an error happened, set to true
    private boolean hasError;
    @JsonProperty
    //error code
    private int errorCode;
    @JsonProperty
    //results (if no error)
    private Result results;

    public Response(boolean hasError, int errorCode, Result results) {
        this.hasError = hasError;
        this.errorCode = errorCode;
        this.results = results;
    }

    /**
     * used only for json (de)serializing
     */
    public Response() {
    }

    public boolean isHasError() {
        return hasError;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Result getResults() {
        return results;
    }

}
