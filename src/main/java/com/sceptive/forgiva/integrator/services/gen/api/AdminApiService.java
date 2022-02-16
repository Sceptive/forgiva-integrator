package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.api.*;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

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

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-06-19T09:05:53.979+03:00[Europe/Istanbul]")
public abstract class AdminApiService {
    public abstract Response postAdminApplicationAdd(PostAdminApplicationAddRequest postAdminApplicationAddRequest
 ) throws NotFoundException;
    public abstract Response postAdminApplicationRemove(PostAdminApplicationRemoveRequest postAdminApplicationRemoveRequest
 ) throws NotFoundException;
    public abstract Response postAdminApplicationUpdate(PostAdminApplicationUpdateRequest postAdminApplicationUpdateRequest
 ) throws NotFoundException;
    public abstract Response postAdminApplications(PostAdminApplicationsRequest postAdminApplicationsRequest
 ) throws NotFoundException;
    public abstract Response postAdminHostAdd(PostAdminHostAddRequest postAdminHostAddRequest
 ) throws NotFoundException;
    public abstract Response postAdminHostRemove(PostAdminHostRemoveRequest postAdminHostRemoveRequest
 ) throws NotFoundException;
    public abstract Response postAdminHostUpdate(PostAdminHostUpdateRequest postAdminHostUpdateRequest
 ) throws NotFoundException;
    public abstract Response postAdminHosts(PostAdminHostsRequest postAdminHostsRequest
 ) throws NotFoundException;
    public abstract Response postAdminReports(PostAdminReportsRequest postAdminReportsRequest
 ) throws NotFoundException;
    public abstract Response postAdminResourceusage(PostAdminResourceusageRequest postAdminResourceusageRequest
 ) throws NotFoundException;
    public abstract Response postAdminSysteminformation(PostAdminSysteminformationRequest postAdminSysteminformationRequest
 ) throws NotFoundException;
    public abstract Response postAdminUserAdd(PostAdminUserAddRequest postAdminUserAddRequest
 ) throws NotFoundException;
    public abstract Response postAdminUserByusergroup(PostAdminUserByusergroupRequest postAdminUserByusergroupRequest
 ) throws NotFoundException;
    public abstract Response postAdminUserRemove(PostAdminUserRemoveRequest postAdminUserRemoveRequest
 ) throws NotFoundException;
    public abstract Response postAdminUserUpdate(PostAdminUserUpdateRequest postAdminUserUpdateRequest
 ) throws NotFoundException;
    public abstract Response postAdminUsergroupAdd(PostAdminUsergroupAddRequest postAdminUsergroupAddRequest
 ) throws NotFoundException;
    public abstract Response postAdminUsergroupRemove(PostAdminUsergroupRemoveRequest postAdminUsergroupRemoveRequest
 ) throws NotFoundException;
    public abstract Response postAdminUsergroupUpdate(PostAdminUsergroupUpdateRequest postAdminUsergroupUpdateRequest
 ) throws NotFoundException;
    public abstract Response postAdminUsergroups(PostAdminUsergroupsRequest postAdminUsergroupsRequest
 ) throws NotFoundException;
}
