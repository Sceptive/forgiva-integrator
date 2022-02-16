package com.sceptive.forgiva.integrator.test;

import com.sceptive.forgiva.integrator.services.gen.model.User;

public class SimpleResponse {

    private int      code;
    private String   body;

    public SimpleResponse code(int code) {
        this.code = code;
        return this;
    }

    public SimpleResponse body(String body) {
        this.body = body;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
