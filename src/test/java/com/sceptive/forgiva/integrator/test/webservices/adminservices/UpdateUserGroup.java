package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupUpdateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.UserGroup;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;
import com.sceptive.forgiva.integrator.logging.Info;

public class UpdateUserGroup implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {
        PostAdminUsergroupUpdateRequest req = new PostAdminUsergroupUpdateRequest();
        req.setHeader(parent.adminDefHeader);
        parent.added_groups.forEach((group_id, group_name) -> {
            req.userGroup(new UserGroup().userGroupId(group_id)
                                         .userGroupName(group_name + "_updated"));
            General.launch_test_request("/admin/user_group/update",
                                        req,
                                        OperationResult.class,
                                        200,
                                        (response) -> {
                                            Assert.assertNotNull(response.getAffectedRecords());
                                            Assert.assertEquals(response.getAffectedRecords()
                                                                        .get(0),
                                                                group_id);
                                        });
        });
        // Check for invalid group id
        req.userGroup(new UserGroup().userGroupId("2139792817397122")
                                     .userGroupName("Will be failed"));
        General.launch_test_request("/admin/user_group/update",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNull(response.getAffectedRecords());
                                        Assert.assertNotNull(response.getError());
                                        Assert.assertTrue(response.getError()
                                                                  .length() > 0);
                                    });
        // Check for non-integer group id
        req.userGroup(new UserGroup().userGroupId("abcdef")
                                     .userGroupName("Will be failed"));
        General.launch_test_request("/admin/user_group/update",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNull(response.getAffectedRecords());
                                        Assert.assertNotNull(response.getError());
                                        Assert.assertTrue(response.getError()
                                                                  .length() > 0);
                                    });
    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("All user groups are updated");

}
}