package com.sceptive.forgiva.integrator.services.gen.api.impl;

import com.sceptive.forgiva.integrator.services.gen.api.AdminApiService;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.services.impl.AdminServices;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaMSF4JServerCodegen",
    date = "2020-02-26T13:56:36.662+03:00[Europe/Istanbul]")
public class AdminApiServiceImpl extends AdminApiService {


@Override
public Response postAdminApplicationAdd(PostAdminApplicationAddRequest postAdminApplicationAddRequest) throws NotFoundException {
    return AdminServices.add_application(postAdminApplicationAddRequest);
}

@Override
public Response postAdminApplicationRemove(PostAdminApplicationRemoveRequest postAdminApplicationRemoveRequest) throws NotFoundException {
    return AdminServices.remove_application(postAdminApplicationRemoveRequest);
}

@Override
public Response postAdminApplicationUpdate(PostAdminApplicationUpdateRequest postAdminApplicationUpdateRequest) throws NotFoundException {
    return AdminServices.update_application(postAdminApplicationUpdateRequest);
}

@Override
public Response postAdminApplications(PostAdminApplicationsRequest postAdminApplicationsRequest) throws NotFoundException {
    return AdminServices.get_applications_by_host(postAdminApplicationsRequest);
}

@Override
public Response postAdminHostAdd(PostAdminHostAddRequest postAdminHostAddRequest) throws NotFoundException {

    return AdminServices.add_host(postAdminHostAddRequest);
}

@Override
public Response postAdminHostRemove(PostAdminHostRemoveRequest postAdminHostRemoveRequest) throws NotFoundException {
    return AdminServices.remove_host(postAdminHostRemoveRequest);
}

@Override
public Response postAdminHostUpdate(PostAdminHostUpdateRequest postAdminHostUpdateRequest) throws NotFoundException {
    return AdminServices.update_host(postAdminHostUpdateRequest);
}

@Override
public Response postAdminHosts(PostAdminHostsRequest postAdminHostsRequest) throws NotFoundException {
    return AdminServices.get_hosts(postAdminHostsRequest);
}


@Override
  public Response postAdminReports(PostAdminReportsRequest postAdminReportsRequest)
      throws NotFoundException {
    return AdminServices.get_reports(postAdminReportsRequest);
  }

  @Override
  public Response postAdminResourceusage(
      PostAdminResourceusageRequest postAdminResourceusageRequest) throws NotFoundException {
    return AdminServices.get_resource_usages(postAdminResourceusageRequest);
  }

  @Override
  public Response postAdminSysteminformation(
      PostAdminSysteminformationRequest postAdminSysteminformationRequest)
      throws NotFoundException {
    return AdminServices.get_system_information(postAdminSysteminformationRequest);
  }

  @Override
  public Response postAdminUserAdd(PostAdminUserAddRequest postAdminUserAddRequest)
      throws NotFoundException {
    return AdminServices.add_user(postAdminUserAddRequest);
  }

  @Override
  public Response postAdminUserByusergroup(
      PostAdminUserByusergroupRequest postAdminUserByusergroupRequest) throws NotFoundException {
    return AdminServices.get_users_by_usergroup(postAdminUserByusergroupRequest);
  }

  @Override
  public Response postAdminUserRemove(PostAdminUserRemoveRequest postAdminUserRemoveRequest)
      throws NotFoundException {
    return AdminServices.remove_user(postAdminUserRemoveRequest);
  }

@Override
public Response postAdminUserUpdate(final PostAdminUserUpdateRequest postAdminUserUpdateRequest)
        throws NotFoundException {
    return AdminServices.update_user(postAdminUserUpdateRequest);
}

@Override
  public Response postAdminUsergroupAdd(PostAdminUsergroupAddRequest postAdminUsergroupAddRequest)
      throws NotFoundException {
    return AdminServices.add_usergroup(postAdminUsergroupAddRequest);
  }

  @Override
  public Response postAdminUsergroupRemove(
      PostAdminUsergroupRemoveRequest postAdminUsergroupRemoveRequest) throws NotFoundException {
    return AdminServices.remove_usergroup(postAdminUsergroupRemoveRequest);
  }

  @Override
  public Response postAdminUsergroupUpdate(
      PostAdminUsergroupUpdateRequest postAdminUsergroupUpdateRequest) throws NotFoundException {
    return AdminServices.update_usergroup(postAdminUsergroupUpdateRequest);
  }

  @Override
  public Response postAdminUsergroups(PostAdminUsergroupsRequest postAdminUsergroupsRequest)
      throws NotFoundException {
    return AdminServices.get_usergroups(postAdminUsergroupsRequest);
  }
}
