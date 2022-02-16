package com.sceptive.forgiva.integrator.services.gen.api.factories;

import com.sceptive.forgiva.integrator.services.gen.api.NewSessionApiService;
import com.sceptive.forgiva.integrator.services.gen.api.impl.NewSessionApiServiceImpl;

public class NewSessionApiServiceFactory {
    private final static NewSessionApiService service = new NewSessionApiServiceImpl();

    public static NewSessionApiService getNewSessionApi() {
        return service;
    }
}
