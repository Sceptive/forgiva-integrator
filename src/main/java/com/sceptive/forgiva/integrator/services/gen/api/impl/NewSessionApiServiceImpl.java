package com.sceptive.forgiva.integrator.services.gen.api.impl;

import com.sceptive.forgiva.integrator.services.gen.api.*;

import com.sceptive.forgiva.integrator.services.gen.model.PostNewSessionRequest;

import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import com.sceptive.forgiva.integrator.services.impl.SessionServices;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public class NewSessionApiServiceImpl extends NewSessionApiService {
    /*
        This service initializes session or validates it against expiration (by checking header object) and provides
        server configuration values to the client.
     */
    @Override
    public Response postNewSession(PostNewSessionRequest postNewSessionRequest) throws NotFoundException {

        return SessionServices.newsession(postNewSessionRequest);
    }
}
