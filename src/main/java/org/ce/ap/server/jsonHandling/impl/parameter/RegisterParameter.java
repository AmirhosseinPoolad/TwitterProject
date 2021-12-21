package main.java.org.ce.ap.server.jsonHandling.impl.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.jsonHandling.Parameter;

import java.time.LocalDate;

public class RegisterParameter extends Parameter {
    @JsonProperty
    String username;
    @JsonProperty
    String password;
    @JsonProperty
    String firstName;
    @JsonProperty
    String lastName;
    @JsonProperty
    String biography;
    @JsonProperty
    LocalDate birthdayDate;

    public RegisterParameter(String username, String password, String firstName, String lastName, String biography, LocalDate birthdayDate) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.birthdayDate = birthdayDate;
    }

    public RegisterParameter() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBiography() {
        return biography;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }
}
