package com.sceptive.forgiva.integrator.services.gen.api.impl;

import com.sceptive.forgiva.integrator.services.gen.api.*;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataSearchRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataSearchResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserGenerateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserGenerateResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataBygroupRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataBygroupResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupsResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataHostRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataHostResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataRemoveRequest;

import java.util.List;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import java.io.InputStream;

import com.sceptive.forgiva.integrator.services.impl.UserServices;
import org.wso2.msf4j.formparam.FormDataParam;
import org.wso2.msf4j.formparam.FileInfo;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public class UserApiServiceImpl extends UserApiService {
    @Override
    public Response postUserGenerate(PostUserGenerateRequest postUserGenerateRequest
 ) throws NotFoundException {
        return UserServices.generate_password(postUserGenerateRequest);
    }

    @Override
    public Response postUserMetadataAdd(PostUserMetadataAddRequest postUserMetadataAddRequest
 ) throws NotFoundException {
        return UserServices.add_metadata(postUserMetadataAddRequest);
    }
    @Override
    public Response postUserMetadataBygroup(PostUserMetadataBygroupRequest postUserMetadataBygroupRequest
 ) throws NotFoundException {
        return UserServices.get_metadata_bygroupId(postUserMetadataBygroupRequest);
    }

    @Override
    public Response postUserMetadataGroupAdd(PostUserMetadataGroupAddRequest postUserMetadataGroupAddRequest) throws NotFoundException {
        return UserServices.add_metadatagroup(postUserMetadataGroupAddRequest);
    }

    @Override
    public Response postUserMetadataGroupRemove(PostUserMetadataGroupRemoveRequest postUserMetadataGroupRemoveRequest
 ) throws NotFoundException {
       return UserServices.remove_metadatagroup(postUserMetadataGroupRemoveRequest);
    }
    @Override
    public Response postUserMetadataGroups(PostUserMetadataGroupsRequest postUserMetadataGroupsRequest
 ) throws NotFoundException {
       return UserServices.get_metadata_groups(postUserMetadataGroupsRequest);
    }
    @Override
    public Response postUserMetadataHost(PostUserMetadataHostRequest postUserMetadataHostRequest
 ) throws NotFoundException {
        return UserServices.get_metadata_hosts(postUserMetadataHostRequest);
    }
    @Override
    public Response postUserMetadataRemove(PostUserMetadataRemoveRequest postUserMetadataRemoveRequest
 ) throws NotFoundException {
        return UserServices.remove_metadata(postUserMetadataRemoveRequest);
    }


    @Override
    public Response postUserMetadataSearch(PostUserMetadataSearchRequest postUserMetadataSearchRequest
 ) throws NotFoundException {
       return UserServices.search_metadata(postUserMetadataSearchRequest);
    }

    @Override
    public Response postUserPasswordChange(
            final PostUserPasswordChangeRequest postUserPasswordChangeRequest)
            throws NotFoundException {
        return UserServices.change_password(postUserPasswordChangeRequest);
    }

    @Override
    public Response postUserSettingsGet(final PostUserSettingsGetRequest postUserSettingsGetRequest)
            throws NotFoundException {
        return UserServices.settings_get(postUserSettingsGetRequest);
    }

    @Override
    public Response postUserSettingsSet(final PostUserSettingsSetRequest postUserSettingsSetRequest)
            throws NotFoundException {
        return UserServices.settings_set(postUserSettingsSetRequest);
    }
}
