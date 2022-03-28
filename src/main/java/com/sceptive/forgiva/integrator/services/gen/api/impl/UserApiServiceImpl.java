package com.sceptive.forgiva.integrator.services.gen.api.impl;

import com.sceptive.forgiva.integrator.services.gen.api.*;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataSearchRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserGenerateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataBygroupRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupRemoveRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataHostRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataRemoveRequest;

import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;

import com.sceptive.forgiva.integrator.services.impl.userservices.*;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen", date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public class UserApiServiceImpl extends UserApiService {
    @Override
    public Response postUser2faDisable(PostUser2faDisableRequest postUser2faDisableRequest) throws NotFoundException {
        return WS2Fa.user_2fa_disable(postUser2faDisableRequest);
    }

    @Override
    public Response postUser2faEnable(PostUser2faEnableRequest postUser2faEnableRequest) throws NotFoundException {
        return WS2Fa.user_2fa_enable(postUser2faEnableRequest);
    }

    @Override
    public Response postUserBackupExport(PostUserBackupExportRequest postUserBackupExportRequest) throws NotFoundException {
        return WSBackup.export_service(postUserBackupExportRequest);
    }

    @Override
    public Response postUserBackupImport(PostUserBackupImportRequest postUserBackupImportRequest) throws NotFoundException {
        return WSBackup.import_service(postUserBackupImportRequest);
    }

    @Override
    public Response postUserGenerate(PostUserGenerateRequest postUserGenerateRequest
    ) throws NotFoundException {
        return WSGeneratePassword.generate_password(postUserGenerateRequest);
    }

    @Override
    public Response postUserMetadataAdd(PostUserMetadataAddRequest postUserMetadataAddRequest
    ) throws NotFoundException {
        return WSMetadata.add_metadata(postUserMetadataAddRequest);
    }
    @Override
    public Response postUserMetadataBygroup(PostUserMetadataBygroupRequest postUserMetadataBygroupRequest
    ) throws NotFoundException {
        return WSMetadata.get_metadata_bygroupId(postUserMetadataBygroupRequest);
    }

    @Override
    public Response postUserMetadataGroupAdd(PostUserMetadataGroupAddRequest postUserMetadataGroupAddRequest) throws NotFoundException {
        return WSMetadataGroup.add_metadatagroup(postUserMetadataGroupAddRequest);
    }

    @Override
    public Response postUserMetadataGroupRemove(PostUserMetadataGroupRemoveRequest postUserMetadataGroupRemoveRequest
    ) throws NotFoundException {
        return WSMetadataGroup.remove_metadatagroup(postUserMetadataGroupRemoveRequest);
    }
    @Override
    public Response postUserMetadataGroups(PostUserMetadataGroupsRequest postUserMetadataGroupsRequest
    ) throws NotFoundException {
        return WSMetadataGroup.get_metadata_groups(postUserMetadataGroupsRequest);
    }
    @Override
    public Response postUserMetadataHost(PostUserMetadataHostRequest postUserMetadataHostRequest
    ) throws NotFoundException {
        return WSMetadata.get_metadata_hosts(postUserMetadataHostRequest);
    }
    @Override
    public Response postUserMetadataRemove(PostUserMetadataRemoveRequest postUserMetadataRemoveRequest
    ) throws NotFoundException {
        return WSMetadata.remove_metadata(postUserMetadataRemoveRequest);
    }

    @Override
    public Response postUserMetadataSearch(PostUserMetadataSearchRequest postUserMetadataSearchRequest
    ) throws NotFoundException {
        return WSMetadata.search_metadata(postUserMetadataSearchRequest);
    }

    @Override
    public Response postUserPasswordChange(
            final PostUserPasswordChangeRequest postUserPasswordChangeRequest)
            throws NotFoundException {
        return WSChangePassword.change_password(postUserPasswordChangeRequest);
    }

    @Override
    public Response postUserSettingsGet(final PostUserSettingsGetRequest postUserSettingsGetRequest)
            throws NotFoundException {
        return WSUserSettings.settings_get(postUserSettingsGetRequest);
    }

    @Override
    public Response postUserSettingsSet(final PostUserSettingsSetRequest postUserSettingsSetRequest)
            throws NotFoundException {
        return WSUserSettings.settings_set(postUserSettingsSetRequest);
    }
}
