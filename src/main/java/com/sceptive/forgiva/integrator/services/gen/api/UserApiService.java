package com.sceptive.forgiva.integrator.services.gen.api;

import com.sceptive.forgiva.integrator.services.gen.api.*;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

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

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-05-29T04:24:28.046+03:00[Europe/Istanbul]")
public abstract class UserApiService {
    public abstract Response postUserGenerate(PostUserGenerateRequest postUserGenerateRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataAdd(PostUserMetadataAddRequest postUserMetadataAddRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataBygroup(PostUserMetadataBygroupRequest postUserMetadataBygroupRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataGroupAdd(PostUserMetadataGroupAddRequest postUserMetadataGroupAddRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataGroupRemove(PostUserMetadataGroupRemoveRequest postUserMetadataGroupRemoveRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataGroups(PostUserMetadataGroupsRequest postUserMetadataGroupsRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataHost(PostUserMetadataHostRequest postUserMetadataHostRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataRemove(PostUserMetadataRemoveRequest postUserMetadataRemoveRequest
 ) throws NotFoundException;
    public abstract Response postUserMetadataSearch(PostUserMetadataSearchRequest postUserMetadataSearchRequest
 ) throws NotFoundException;
    public abstract Response postUserPasswordChange(PostUserPasswordChangeRequest postUserPasswordChangeRequest
 ) throws NotFoundException;
    public abstract Response postUserSettingsGet(PostUserSettingsGetRequest postUserSettingsGetRequest
 ) throws NotFoundException;
    public abstract Response postUserSettingsSet(PostUserSettingsSetRequest postUserSettingsSetRequest
 ) throws NotFoundException;
}
