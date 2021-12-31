package main.java.org.ce.ap.server.jsonHandling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * server request object
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Request {
    @JsonProperty
    //requested method
    private String method;
    @JsonProperty
    //description of method
    private String description;
    @JsonProperty
    //request parameters
    private Parameter parameterValues;

    public Request(String method, String description, Parameter parameterValues) {
        this.method = method;
        this.description = description;
        this.parameterValues = parameterValues;
    }

    /**
     * used only for json (de)serializing
     */
    public Request() {

    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public Parameter getParameterValues() {
        return parameterValues;
    }
}
