package com.sceptive.forgiva.integrator.test.webservices;

import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.webservices.userservices.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Vector;


@Test(dependsOnGroups = "login-tests")
public class UserServicesTests extends IFTests {

  public static final String[] test_hosts =
      new String[] {
        "microsoft.com",
        "gmail.com",
        "abc.com",
        "192.168.0.1",
        "crm.metacortex.com",
        "portal.metacortex.com",
        "10.0.0.3"
      };

  public static final String[] test_accounts =
      new String[] {
        "bill", "dominic", "nebula", "root", "Administrator", "domain/mysuperuser", "marcus"
      };

  public  static final int test_iterations = 50;

  /**
   *  Holds header information for logged in user's
   * */
  public  Header            session_header = null;

  public  Vector<Long> added_metadata_group_ids                     = new Vector<>(0);
  public  Vector<Long> added_metadata_ids                           = new Vector<>(0);
  public  HashMap<Long, Integer> added_metadata_counts_bygroupid    = new HashMap<>(0);
  public  HashMap<String, Integer> added_keyword_record_counts      = new HashMap<>(0);
  public  HashMap<Long, Metadata> added_metadatass                  = new HashMap<>(0);
  public  int removed_by_group_membership = 0;

  public static void increase(HashMap<String, Integer> hm, String key) {

    hm.merge(key, 1, (a, b) -> a + b);
  }

  @BeforeClass(alwaysRun = true)
  public void setup() {
    General.setup_web_services();
    ;

    try {

      General.create_and_login_as_test_user(
          (ps_response, pl_response, header, client_pk) -> {
              session_header = header;
          });

    } catch (Exception e) {
      Fatal.get_instance().print(e);
      Assert.fail("Has exception creating test users");
    }
  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "add_metadatagroup",
      description = "Testing adding new metadata groups",
      priority = 1)
  public void test_add_metadatagroup() {

      new AddMetadataGroup().launch(this);

  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "add_metadata",
      description = "Testing adding new metadata",
      priority = 1,
      dependsOnMethods = "test_add_metadatagroup")
  public void test_add_metadata() {
      new AddMetadata().launch(this);
  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "get_metadata_bygroupId",
      description = "Testing receiving metadatas by group ids",
      priority = 1,
      dependsOnMethods = "test_add_metadata")
  public void test_get_metadata_bygroupId() {

      new GetMetadataByGroupId().launch(this);
  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "search_metadata",
      description = "Testing searching metadata",
      priority = 1,
      dependsOnMethods = "test_add_metadata")
  public void test_search_metadata() {

    new SearchMetadata().launch(this);
  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "generate_password",
      description = "Testing password generation",
      priority = 1)
  public void test_generate_password() {
    // TODO Needs to get implemented
  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "get_metadata_groups",
      description = "Testing receiving metadata groups",
      priority = 1,
      dependsOnMethods = "test_add_metadatagroup")
  public void test_get_metadata_groups() {

      new GetMetadataGroups().launch(this);

  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "get_metadata_hosts",
      description = "Testing receiving metadata hosts",
      priority = 1,
      dependsOnMethods = "test_add_metadata")
  public void test_get_metadata_hosts() {

    new GetMetadataHosts().launch(this);
  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "remove_metadatagroup",
      description = "Testing metadata removal",
      priority = 1,
      dependsOnMethods = {
        "test_add_metadata",
        "test_get_metadata_hosts",
        "test_get_metadata_groups",
        "test_search_metadata",
        "test_get_metadata_bygroupId",
        "test_add_metadata"
      })
  public void test_remove_metadatagroup() {

      new RemoveMetadataGroup().launch(this);
  }

  @Test(
      groups = {"user-services-tests", "web-services-tests"},
      testName = "remove_metadata",
      description = "Testing removing metadatas",
      priority = 2,
      dependsOnMethods = "test_remove_metadatagroup")
  public void test_remove_metadata() {

    new RemoveMetadata().launch(this);
  }

@Test(
        groups = {"user-services-tests", "web-services-tests"},
        testName = "change_password",
        description = "Testing internal user password change",
        priority = 1,
        dependsOnMethods = {
                "test_remove_metadata"
        })
public void test_change_password() {

    new ChangePassword().launch(this);
}

  @Test(
          groups = {"user-services-tests", "web-services-tests"},
          testName = "backups",
          description = "Testing backing-up mechanisms",
          priority = 1,
          dependsOnMethods = {
                  "test_remove_metadata"
          })
  public void test_backups() {

    new Backup().launch(this);
  }
}
