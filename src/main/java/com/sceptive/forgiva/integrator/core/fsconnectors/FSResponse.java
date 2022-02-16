package com.sceptive.forgiva.integrator.core.fsconnectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FSResponse {
    @JsonProperty("animal")
    private String animal;
    @JsonProperty("password")
    private String password;

    public FSResponse() {

    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(final String _animal) {
        animal = _animal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String _password) {
        password = _password;
    }
}
