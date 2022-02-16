package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.services.gen.api.UserApiService;
import com.sceptive.forgiva.integrator.services.gen.api.factories.UserApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserGenerateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserGenerateResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataBygroupRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataBygroupResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupsResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataHostRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataHostResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataSearchRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataSearchResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserPasswordChangeRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserSettingsGetRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserSettingsGetResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserSettingsSetRequest;

import java.util.List;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import java.io.InputStream;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/user")


@io.swagger.annotations.Api(description = "the user API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-29T04:24:28.046+03:00[Europe/Istanbul]")
public class UserApi  {
   private final UserApiService delegate = UserApiServiceFactory.getUserApi();

    @POST
    @Path("/generate")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Generating password", notes = "Generates password specified with metadata, master password and visual confirmation data.", response = PostUserGenerateResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = PostUserGenerateResponse.class) })
    public Response postUserGenerate(@ApiParam(value = "" ) PostUserGenerateRequest postUserGenerateRequest
)
    throws NotFoundException {
        return delegate.postUserGenerate(postUserGenerateRequest);
    }
    @POST
    @Path("/metadata/add")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding metadata", notes = "Adds metadata to the specified group.", response = OperationResult.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class) })
    public Response postUserMetadataAdd(@ApiParam(value = "" ) PostUserMetadataAddRequest postUserMetadataAddRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataAdd(postUserMetadataAddRequest);
    }
    @POST
    @Path("/metadata/by_group")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieving metadata", notes = "Returns metadatas for the user.", response = PostUserMetadataBygroupResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostUserMetadataBygroupResponse.class) })
    public Response postUserMetadataBygroup(@ApiParam(value = "" ) PostUserMetadataBygroupRequest postUserMetadataBygroupRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataBygroup(postUserMetadataBygroupRequest);
    }
    @POST
    @Path("/metadata/group/add")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding group", notes = "Adds metadata group specified within the body", response = OperationResult.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class) })
    public Response postUserMetadataGroupAdd(@ApiParam(value = "" ) PostUserMetadataGroupAddRequest postUserMetadataGroupAddRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataGroupAdd(postUserMetadataGroupAddRequest);
    }
    @POST
    @Path("/metadata/group/remove")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing group", notes = "Removes metadata group specified with group id.", response = OperationResult.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class) })
    public Response postUserMetadataGroupRemove(@ApiParam(value = "" ) PostUserMetadataGroupRemoveRequest postUserMetadataGroupRemoveRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataGroupRemove(postUserMetadataGroupRemoveRequest);
    }
    @POST
    @Path("/metadata/groups")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieving metadata groups", notes = "Returns metadata groups for the user.", response = PostUserMetadataGroupsResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostUserMetadataGroupsResponse.class) })
    public Response postUserMetadataGroups(@ApiParam(value = "" ) PostUserMetadataGroupsRequest postUserMetadataGroupsRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataGroups(postUserMetadataGroupsRequest);
    }
    @POST
    @Path("/metadata/host")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieving metadata host", notes = "Returns hosts (if specified) filtered", response = PostUserMetadataHostResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostUserMetadataHostResponse.class) })
    public Response postUserMetadataHost(@ApiParam(value = "" ) PostUserMetadataHostRequest postUserMetadataHostRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataHost(postUserMetadataHostRequest);
    }
    @POST
    @Path("/metadata/remove")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing metadata", notes = "Removes metadata specified with metadata id.", response = OperationResult.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class) })
    public Response postUserMetadataRemove(@ApiParam(value = "" ) PostUserMetadataRemoveRequest postUserMetadataRemoveRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataRemove(postUserMetadataRemoveRequest);
    }
    @POST
    @Path("/metadata/search")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Searching metadata", notes = "Returns metadatas searched with 'criteria'.", response = PostUserMetadataSearchResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostUserMetadataSearchResponse.class) })
    public Response postUserMetadataSearch(@ApiParam(value = "" ) PostUserMetadataSearchRequest postUserMetadataSearchRequest
)
    throws NotFoundException {
        return delegate.postUserMetadataSearch(postUserMetadataSearchRequest);
    }
    @POST
    @Path("/password/change")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Changing user's password", notes = "Changes user's password with specified ones and invalidates all active sessions.", response = OperationResult.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = OperationResult.class) })
    public Response postUserPasswordChange(@ApiParam(value = "" ) PostUserPasswordChangeRequest postUserPasswordChangeRequest
)
    throws NotFoundException {
        return delegate.postUserPasswordChange(postUserPasswordChangeRequest);
    }
    @POST
    @Path("/settings/get")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Getting user's choices", notes = "Getting user's custom settings set by user", response = PostUserSettingsGetResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = PostUserSettingsGetResponse.class) })
    public Response postUserSettingsGet(@ApiParam(value = "" ) PostUserSettingsGetRequest postUserSettingsGetRequest
)
    throws NotFoundException {
        return delegate.postUserSettingsGet(postUserSettingsGetRequest);
    }
    @POST
    @Path("/settings/set")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Setting user's choices", notes = "Setting user's custom setting value", response = OperationResult.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "", response = OperationResult.class) })
    public Response postUserSettingsSet(@ApiParam(value = "" ) PostUserSettingsSetRequest postUserSettingsSetRequest
)
    throws NotFoundException {
        return delegate.postUserSettingsSet(postUserSettingsSetRequest);
    }
}
