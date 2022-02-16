package com.sceptive.forgiva.integrator.services.gen.api.factories;

import com.sceptive.forgiva.integrator.services.gen.api.LoginApiService;
import com.sceptive.forgiva.integrator.services.gen.api.impl.LoginApiServiceImpl;

public class LoginApiServiceFactory {
    private final static LoginApiService service = new LoginApiServiceImpl();

    public static LoginApiService getLoginApi() {
        return service;
    }
}
