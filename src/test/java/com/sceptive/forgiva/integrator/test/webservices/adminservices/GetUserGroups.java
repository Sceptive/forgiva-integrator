package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUsergroupsResponse;
import com.sceptive.forgiva.integrator.services.gen.model.UserGroup;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class GetUserGroups implements ITest {
@Override
public void launch(final IFTests _parent) {


    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {
        PostAdminUsergroupsRequest _req = new PostAdminUsergroupsRequest();
        _req.setHeader(parent.adminDefHeader);
        General.launch_test_request("/admin/user_groups",
                                    _req,
                                    PostAdminUsergroupsResponse.class,
                                    200,
                                    (resp) -> {
                                        Assert.assertNotNull(resp.getUserGroups());
                                        Info.get_instance()
                                            .print("Received %d user groups",
                                                   resp.getUserGroups()
                                                       .size());
                                        for (UserGroup gr : resp.getUserGroups()) {
                                            String group_name = parent.added_groups.get(gr.getUserGroupId());
                                            Assert.assertNotNull(group_name);
                                            Assert.assertTrue(group_name.equals(gr.getUserGroupName()));
                                        }
                                    });
    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Get %d user groups", parent.added_groups.size());
}
}