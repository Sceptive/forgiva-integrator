package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.services.gen.api.AdminApiService;
import com.sceptive.forgiva.integrator.services.gen.api.factories.AdminApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminApplicationAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminApplicationRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminApplicationUpdateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminApplicationsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminApplicationsResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostUpdateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostsResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminReportsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminReportsResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminResourceusageRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminResourceusageResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminSysteminformationRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminSysteminformationResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserByusergroupRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserByusergroupResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserUpdateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupUpdateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupsResponse;

import java.util.List;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import java.io.InputStream;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/admin")


@io.swagger.annotations.Api(description = "the admin API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T09:05:53.979+03:00[Europe/Istanbul]")
public class AdminApi  {
   private final AdminApiService delegate = AdminApiServiceFactory.getAdminApi();

    @POST
    @Path("/application/add")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding a new application", notes = "Adds a new application described within the post body.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminApplicationAdd(@ApiParam(value = "" ) PostAdminApplicationAddRequest postAdminApplicationAddRequest
)
    throws NotFoundException {
        return delegate.postAdminApplicationAdd(postAdminApplicationAddRequest);
    }
    @POST
    @Path("/application/remove")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing application", notes = "Removes application given in the body with application id.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminApplicationRemove(@ApiParam(value = "" ) PostAdminApplicationRemoveRequest postAdminApplicationRemoveRequest
)
    throws NotFoundException {
        return delegate.postAdminApplicationRemove(postAdminApplicationRemoveRequest);
    }
    @POST
    @Path("/application/update")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Updating application", notes = "Updates application specified within the body tagged with application id.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminApplicationUpdate(@ApiParam(value = "" ) PostAdminApplicationUpdateRequest postAdminApplicationUpdateRequest
)
    throws NotFoundException {
        return delegate.postAdminApplicationUpdate(postAdminApplicationUpdateRequest);
    }
    @POST
    @Path("/applications/by_host")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Getting applications", notes = "Return applications.", response = PostAdminApplicationsResponse.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostAdminApplicationsResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = PostAdminApplicationsResponse.class) })
    public Response postAdminApplications(@ApiParam(value = "" ) PostAdminApplicationsRequest postAdminApplicationsRequest
)
    throws NotFoundException {
        return delegate.postAdminApplications(postAdminApplicationsRequest);
    }
    @POST
    @Path("/host/add")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding host", notes = "Adds host specified within the body", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminHostAdd(@ApiParam(value = "" ) PostAdminHostAddRequest postAdminHostAddRequest
)
    throws NotFoundException {
        return delegate.postAdminHostAdd(postAdminHostAddRequest);
    }
    @POST
    @Path("/host/remove")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing host", notes = "Removes host specified with host id.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminHostRemove(@ApiParam(value = "" ) PostAdminHostRemoveRequest postAdminHostRemoveRequest
)
    throws NotFoundException {
        return delegate.postAdminHostRemove(postAdminHostRemoveRequest);
    }
    @POST
    @Path("/host/update")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Updating host", notes = "Updates host specified within the body tagged with host id.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminHostUpdate(@ApiParam(value = "" ) PostAdminHostUpdateRequest postAdminHostUpdateRequest
)
    throws NotFoundException {
        return delegate.postAdminHostUpdate(postAdminHostUpdateRequest);
    }
    @POST
    @Path("/hosts")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Getting hosts", notes = "Return hosts.", response = PostAdminHostsResponse.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostAdminHostsResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = PostAdminHostsResponse.class) })
    public Response postAdminHosts(@ApiParam(value = "" ) PostAdminHostsRequest postAdminHostsRequest
)
    throws NotFoundException {
        return delegate.postAdminHosts(postAdminHostsRequest);
    }
    @POST
    @Path("/reports")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Listing available reports", notes = "", response = PostAdminReportsResponse.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostAdminReportsResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = PostAdminReportsResponse.class) })
    public Response postAdminReports(@ApiParam(value = "" ) PostAdminReportsRequest postAdminReportsRequest
)
    throws NotFoundException {
        return delegate.postAdminReports(postAdminReportsRequest);
    }
    @POST
    @Path("/resource_usage")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Getting resource usage data", notes = "Returns resource usage data on the fly", response = PostAdminResourceusageResponse.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostAdminResourceusageResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = PostAdminResourceusageResponse.class) })
    public Response postAdminResourceusage(@ApiParam(value = "" ) PostAdminResourceusageRequest postAdminResourceusageRequest
)
    throws NotFoundException {
        return delegate.postAdminResourceusage(postAdminResourceusageRequest);
    }
    @POST
    @Path("/system_information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Getting server system information", notes = "Returns system information data", response = PostAdminSysteminformationResponse.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostAdminSysteminformationResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = PostAdminSysteminformationResponse.class) })
    public Response postAdminSysteminformation(@ApiParam(value = "" ) PostAdminSysteminformationRequest postAdminSysteminformationRequest
)
    throws NotFoundException {
        return delegate.postAdminSysteminformation(postAdminSysteminformationRequest);
    }
    @POST
    @Path("/user/add")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding a new user", notes = "Adds a new user described within the post body.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminUserAdd(@ApiParam(value = "" ) PostAdminUserAddRequest postAdminUserAddRequest
)
    throws NotFoundException {
        return delegate.postAdminUserAdd(postAdminUserAddRequest);
    }
    @POST
    @Path("/user/by_user_group")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Getting users by user group id", notes = "Returns user data by group id.", response = PostAdminUserByusergroupResponse.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostAdminUserByusergroupResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = PostAdminUserByusergroupResponse.class) })
    public Response postAdminUserByusergroup(@ApiParam(value = "" ) PostAdminUserByusergroupRequest postAdminUserByusergroupRequest
)
    throws NotFoundException {
        return delegate.postAdminUserByusergroup(postAdminUserByusergroupRequest);
    }
    @POST
    @Path("/user/remove")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing user", notes = "Removes user given in the body with user id.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminUserRemove(@ApiParam(value = "" ) PostAdminUserRemoveRequest postAdminUserRemoveRequest
)
    throws NotFoundException {
        return delegate.postAdminUserRemove(postAdminUserRemoveRequest);
    }
    @POST
    @Path("/user/update")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Updating user", notes = "", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminUserUpdate(@ApiParam(value = "" ) PostAdminUserUpdateRequest postAdminUserUpdateRequest
)
    throws NotFoundException {
        return delegate.postAdminUserUpdate(postAdminUserUpdateRequest);
    }
    @POST
    @Path("/user_group/add")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adding user group", notes = "Adds group specified within the body", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminUsergroupAdd(@ApiParam(value = "" ) PostAdminUsergroupAddRequest postAdminUsergroupAddRequest
)
    throws NotFoundException {
        return delegate.postAdminUsergroupAdd(postAdminUsergroupAddRequest);
    }
    @POST
    @Path("/user_group/remove")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Removing user group", notes = "Removes user group specified with group id.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminUsergroupRemove(@ApiParam(value = "" ) PostAdminUsergroupRemoveRequest postAdminUsergroupRemoveRequest
)
    throws NotFoundException {
        return delegate.postAdminUsergroupRemove(postAdminUsergroupRemoveRequest);
    }
    @POST
    @Path("/user_group/update")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Updating user group", notes = "Updates user group specified within the body tagged with group id.", response = OperationResult.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = OperationResult.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = OperationResult.class) })
    public Response postAdminUsergroupUpdate(@ApiParam(value = "" ) PostAdminUsergroupUpdateRequest postAdminUsergroupUpdateRequest
)
    throws NotFoundException {
        return delegate.postAdminUsergroupUpdate(postAdminUsergroupUpdateRequest);
    }
    @POST
    @Path("/user_groups")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Getting user groups", notes = "Return user groups.", response = PostAdminUsergroupsResponse.class, tags={ "Administrator Operations", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PostAdminUsergroupsResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Returns if user does not have admin rights.", response = PostAdminUsergroupsResponse.class) })
    public Response postAdminUsergroups(@ApiParam(value = "" ) PostAdminUsergroupsRequest postAdminUsergroupsRequest
)
    throws NotFoundException {
        return delegate.postAdminUsergroups(postAdminUsergroupsRequest);
    }
}
