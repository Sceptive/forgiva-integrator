package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.UserGroup;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class AddUserGroup implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;
    try {

        final String[] recent_group_id = {null};
        for (int i = 0; i < parent.test_iterations; i++) {
            PostAdminUsergroupAddRequest req = new PostAdminUsergroupAddRequest();
            req.setHeader(parent.adminDefHeader);
            req.group(new UserGroup().userGroupName("Test UserGroup " + i)
                                     .userGroupDescription("Description " + i));
            if (recent_group_id[0] != null && Common.random_boolean()) {
                req.getGroup()
                   .userGroupId(recent_group_id[0]);
            }
            General.launch_test_request("/admin/user_group/add",
                                        req,
                                        OperationResult.class,
                                        200,
                                        (response) -> {
                                            Assert.assertEquals(response.getAffectedRecords()
                                                                        .size(),
                                                                1);
                                            parent.added_groups.put(response.getAffectedRecords()
                                                                            .get(0),
                                                                    req.getGroup()
                                                                       .getUserGroupName());
                                            if (Common.random_boolean()) {
                                                recent_group_id[0] = response.getAffectedRecords()
                                                                             .get(0);
                                            }
                                        });
        }
    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance()
        .print("Added %d new user groups",
               parent.test_iterations);
}
}