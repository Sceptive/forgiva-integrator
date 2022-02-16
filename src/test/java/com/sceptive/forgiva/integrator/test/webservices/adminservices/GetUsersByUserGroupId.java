package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserByusergroupRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserByusergroupResponse;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class GetUsersByUserGroupId implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;
    try {
        PostAdminUserByusergroupRequest _req = new PostAdminUserByusergroupRequest();
        _req.setHeader(parent.adminDefHeader);
        for (String ug_id : parent.added_user_counts_by_group.keySet()) {
            int expected_amount = parent.added_user_counts_by_group.get(ug_id);
            _req.userGroupId(ug_id);
            _req.startIdx(0)
                .count(parent.test_iterations);

            General.launch_test_request("/admin/user/by_user_group",
                                        _req,
                                        PostAdminUserByusergroupResponse.class,
                                        200,
                                        (resp) -> {
                                            Assert.assertNotNull(resp.getUsers());
                                            Assert.assertEquals(resp.getUsers()
                                                                    .size(),
                                                                expected_amount);

                                            Info.get_instance().print("Response", resp);

                                        });
        }
    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Get %d users by user group id ", parent.added_user_counts_by_group.size());
}
}