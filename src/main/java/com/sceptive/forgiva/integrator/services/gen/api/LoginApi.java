package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.api.factories.LoginApiServiceFactory;
import com.sceptive.forgiva.integrator.services.gen.model.PostLoginResponse;
import io.swagger.annotations.ApiParam;
import com.sceptive.forgiva.integrator.services.gen.model.PostLoginRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/login")


@io.swagger.annotations.Api(description = "the login API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public class LoginApi  {
   private final LoginApiService delegate = LoginApiServiceFactory.getLoginApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Login", notes = "Actual logging-in web service holds single point authentication mechanism.", response = PostLoginResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostLoginResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 440, message = "Session Expired", response = PostLoginResponse.class) })
    public Response postLogin(@ApiParam(value = "" ) PostLoginRequest postLoginRequest
)
    throws NotFoundException {
        return delegate.postLogin(postLoginRequest);
    }
}
