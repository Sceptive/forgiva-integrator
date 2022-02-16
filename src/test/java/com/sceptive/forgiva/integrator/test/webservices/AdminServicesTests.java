package com.sceptive.forgiva.integrator.test.webservices;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.AsymmetricKeyPair;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.Crypto;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.gen.api.AdminApi;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.webservices.adminservices.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.HashMap;

@Test(dependsOnGroups = "user-services-tests")
public class AdminServicesTests extends IFTests {
public static final int                      test_iterations            = 50;
public              Header                   userDefHeader              = null;
public              Header                   adminDefHeader             = null;
public              PostNewSessionResponse   pnsr_response              = null;
public              HashMap<String, String>  added_users                = new HashMap<>(0);
public              HashMap<String, Integer> added_user_counts_by_group = new HashMap<>();
public              HashMap<String, String>  added_groups               = new HashMap<>(0);
public              HashMap<String, String>  added_hosts                = new HashMap<>(0);
public              HashMap<String, Long>    added_applications         = new HashMap<>(0);
public              HashMap<String, Integer> added_application_counts_by_host = new HashMap<>();
public              AsymmetricKeyPair        client_kp                  =
        Asymmetric.generate_asymmetric_key_pairs();

@BeforeClass(alwaysRun = true)
public void setup() {
    General.setup_web_services();
    try {
        General.login_as_ex((ps_response, pl_response, header,client_pk) -> {
                                pnsr_response  = ps_response;
                                adminDefHeader = header;
                            },
                            client_kp,
                            Configuration.runtime.administrator_username,
                            "development");
    }
    catch (Exception e) {
        Warning.get_instance()
               .print(e);
        Assert.fail(e.getMessage());
    }
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
      testName = "test_user_access_to_admin_pages",
      description = "Testing user accesses to admin pages",
      priority = 1)
public void test_user_access_to_admin_pages() {
    new AccessToAdminPages().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
      testName = "test_admin_usergroup_add",
      description = "Testing adding user group(s)",
      priority = 1)
public void test_admin_usergroup_add() {
    new AddUserGroup().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
      testName = "test_admin_user_add",
      description = "Testing adding user(s)",
      dependsOnMethods = "test_admin_usergroup_add",
      priority = 1)
public void test_admin_user_add() throws Exception {
    new AddUser().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
      testName = "test_admin_usergroups",
      description = "Testing adding user(s)",
      dependsOnMethods = "test_admin_usergroup_add",
      priority = 1)
public void test_admin_usergroups() {
    PostAdminUsergroupsRequest _req = new PostAdminUsergroupsRequest();
    new GetUserGroups().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
      testName = "test_admin_user_by_usergroup",
      description = "Testing to get users by group id",
      dependsOnMethods = "test_admin_usergroup_add",
      priority = 1)
public void test_admin_user_by_usergroup() {
    new GetUsersByUserGroupId().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
      testName = "test_admin_usergroup_update",
      description = "Testing user group update(s)",
      dependsOnMethods = "test_admin_usergroups",
      priority = 1)
public void test_admin_usergroup_update() {
    new UpdateUserGroup().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
      testName = "test_admin_user_remove",
      description = "Testing removing users",
      dependsOnMethods = "test_admin_usergroup_update",
      priority = 1)
public void test_admin_user_remove() {

     new RemoveUser().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_usergroup_remove",
            description = "Testing removing user groups",
            dependsOnMethods = "test_admin_user_remove",
            priority = 1)
public void test_admin_usergroup_remove() {

        new RemoveUserGroup().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_host_add",
            description = "Testing adding host(s)",
            dependsOnMethods = "test_admin_usergroup_remove",
            priority = 1)
public void test_admin_host_add() {

        new AddHost().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_application_add",
            description = "Testing adding application(s)",
            dependsOnMethods = "test_admin_host_add",
            priority = 1)
public void test_admin_application_add() {

        new AddApplication().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_hosts",
            description = "Testing to get host(s)",
            dependsOnMethods = "test_admin_application_add",
            priority = 1)
public void test_admin_hosts() {

        new GetHosts().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_applications",
            description = "Testing to get application(s)",
            dependsOnMethods = "test_admin_hosts",
             priority = 1)
public void test_admin_applications() {

        new GetApplications().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_applications_by_host",
            description = "Testing to get application(s) by host id",
            dependsOnMethods = "test_admin_applications",
            priority = 1)
public void test_admin_applications_by_host() {

        new GetApplicationsByHostId().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_host_update",
            description = "Testing host update(s)",
            dependsOnMethods = "test_admin_applications_by_host",
            priority = 1)
public void test_admin_host_update() {

        new UpdateHost().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_application_update",
            description = "Testing application update(s)",
            dependsOnMethods = "test_admin_host_update",
            priority = 1)
public void test_admin_application_update() {

        new UpdateApplication().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_application_remove",
            description = "Testing removing applications",
            dependsOnMethods = "test_admin_application_update",
            priority = 1)
public void test_admin_application_remove() {

        new RemoveApplication().launch(this);
}

@Test(groups = {"admin-services-tests", "web-services-tests "},
            testName = "test_admin_host_remove",
            description = "Testing removing host(s)",
            dependsOnMethods = "test_admin_application_remove",
            priority = 1)
public void test_admin_host_remove() {

        new RemoveHost().launch(this);
}

}
