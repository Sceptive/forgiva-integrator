package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.model.PostLoginRequest;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public abstract class LoginApiService {
    public abstract Response postLogin(PostLoginRequest postLoginRequest
 ) throws NotFoundException;
}
