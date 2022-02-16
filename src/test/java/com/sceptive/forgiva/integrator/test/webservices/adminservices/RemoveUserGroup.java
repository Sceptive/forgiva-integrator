package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupRemoveRequest;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class RemoveUserGroup implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {
        PostAdminUsergroupRemoveRequest req = new PostAdminUsergroupRemoveRequest();
        req.setHeader(parent.adminDefHeader);
        // Check for invalid value
        req.userGroupId("123123123123312312");
        General.launch_test_request("/admin/user_group/remove",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNull(response.getError());
                                    });
        final int[] total_removed_count = new int[]{0};
        final int[] expected_removed_count = {0};
        parent.added_user_counts_by_group.forEach((group_id, count) -> {
            req.userGroupId(group_id);
            General.launch_test_request("/admin/user_group/remove",
                                        req,
                                        OperationResult.class,
                                        200,
                                        (response) -> {
                                            if (response.getInfo() != null) {
                                                total_removed_count[0] +=
                                                        Integer.parseInt(response.getInfo()
                                                                                 .split(" ")[0]);
                                            }
                                        });
            expected_removed_count[0] += count;
        });
        //    Info.get_instance()
        //        .print(" Expected %d - Removed %d",
        //               expected_removed_count[0],
        //               total_removed_count[0]);
        // One we removed in user removal test so total removed count should be less than expected
        Assert.assertTrue(((expected_removed_count[0]) - 1) == total_removed_count[0]);


    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Remove user groups");
}
}