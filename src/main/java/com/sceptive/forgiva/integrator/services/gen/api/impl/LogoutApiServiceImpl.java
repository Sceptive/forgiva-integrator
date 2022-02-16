package com.sceptive.forgiva.integrator.services.gen.api.impl;

import com.sceptive.forgiva.integrator.services.gen.api.*;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import com.sceptive.forgiva.integrator.services.gen.model.PostLogoutRequest;

import java.util.List;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import java.io.InputStream;

import com.sceptive.forgiva.integrator.services.impl.SessionServices;
import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-29T23:54:12.971+03:00[Europe/Istanbul]")
public class LogoutApiServiceImpl extends LogoutApiService {
    @Override
    public Response postLogout(PostLogoutRequest postLogoutRequest) throws NotFoundException {

        return SessionServices.logout(postLogoutRequest);
    }
}
