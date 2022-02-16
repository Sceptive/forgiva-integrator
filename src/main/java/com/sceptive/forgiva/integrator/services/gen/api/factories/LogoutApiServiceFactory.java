package com.sceptive.forgiva.integrator.services.gen.api.factories;

import com.sceptive.forgiva.integrator.services.gen.api.LogoutApiService;
import com.sceptive.forgiva.integrator.services.gen.api.impl.LogoutApiServiceImpl;

public class LogoutApiServiceFactory {
    private final static LogoutApiService service = new LogoutApiServiceImpl();

    public static LogoutApiService getLogoutApi() {
        return service;
    }
}
