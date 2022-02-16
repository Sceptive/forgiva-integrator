package com.sceptive.forgiva.integrator.services.gen.api.factories;

import com.sceptive.forgiva.integrator.services.gen.api.Login2faApiService;
import com.sceptive.forgiva.integrator.services.gen.api.impl.Login2faApiServiceImpl;

public class Login2faApiServiceFactory {
    private final static Login2faApiService service = new Login2faApiServiceImpl();

    public static Login2faApiService getLogin2faApi() {
        return service;
    }
}
