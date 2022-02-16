package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.services.gen.api.LogoutApiService;
import com.sceptive.forgiva.integrator.services.gen.api.factories.LogoutApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import com.sceptive.forgiva.integrator.services.gen.model.PostLogoutRequest;

import java.util.List;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import java.io.InputStream;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/logout")


@io.swagger.annotations.Api(description = "the logout API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-29T23:54:12.971+03:00[Europe/Istanbul]")
public class LogoutApi  {
   private final LogoutApiService delegate = LogoutApiServiceFactory.getLogoutApi();

    @POST
    
    @Consumes({ "application/json" })
    
    @io.swagger.annotations.ApiOperation(value = "Logout", notes = "Logs out (and invalidates) session with sessionId", response = Void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = Void.class) })
    public Response postLogout(@ApiParam(value = "" ) PostLogoutRequest postLogoutRequest
)
    throws NotFoundException {
        return delegate.postLogout(postLogoutRequest);
    }
}
