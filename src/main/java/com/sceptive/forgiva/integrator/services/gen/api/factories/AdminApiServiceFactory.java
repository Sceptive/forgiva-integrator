package com.sceptive.forgiva.integrator.services.gen.api.factories;

import com.sceptive.forgiva.integrator.services.gen.api.AdminApiService;
import com.sceptive.forgiva.integrator.services.gen.api.impl.AdminApiServiceImpl;

public class AdminApiServiceFactory {
    private final static AdminApiService service = new AdminApiServiceImpl();

    public static AdminApiService getAdminApi() {
        return service;
    }
}
