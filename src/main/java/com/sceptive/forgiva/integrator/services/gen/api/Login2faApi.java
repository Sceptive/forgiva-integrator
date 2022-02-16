package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.services.gen.api.Login2faApiService;
import com.sceptive.forgiva.integrator.services.gen.api.factories.Login2faApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import com.sceptive.forgiva.integrator.services.gen.model.PostLogin2faRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostLogin2faResponse;

import java.util.List;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import java.io.InputStream;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/login2fa")


@io.swagger.annotations.Api(description = "the login2fa API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public class Login2faApi  {
   private final Login2faApiService delegate = Login2faApiServiceFactory.getLogin2faApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Login with two-factor-authentication", notes = "Two-factor-authentication (2FA) code delivery web service. Not required by default if did not configured on server side.", response = PostLogin2faResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostLogin2faResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 440, message = "Session Expired", response = PostLogin2faResponse.class) })
    public Response postLogin2fa(@ApiParam(value = "" ) PostLogin2faRequest postLogin2faRequest
)
    throws NotFoundException {
        return delegate.postLogin2fa(postLogin2faRequest);
    }
}
