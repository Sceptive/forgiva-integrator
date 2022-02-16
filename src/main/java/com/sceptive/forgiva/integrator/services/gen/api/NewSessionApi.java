package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.api.factories.NewSessionApiServiceFactory;
import com.sceptive.forgiva.integrator.services.gen.model.PostNewSessionResponse;
import io.swagger.annotations.ApiParam;
import com.sceptive.forgiva.integrator.services.gen.model.PostNewSessionRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/new_session")


@io.swagger.annotations.Api(description = "the new_session API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public class NewSessionApi  {
   private final NewSessionApiService delegate = NewSessionApiServiceFactory.getNewSessionApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Initialization or validation of a session", notes = "This service initializes session or validates it (by checking header object) and provides server configuration values to the client.", response = PostNewSessionResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostNewSessionResponse.class) })
    public Response postNewSession(@ApiParam(value = "" ) PostNewSessionRequest postNewSessionRequest
)
    throws NotFoundException {
        return delegate.postNewSession(postNewSessionRequest);
    }
}
