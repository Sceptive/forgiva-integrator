package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserRemoveRequest;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class RemoveUser implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {
        PostAdminUserRemoveRequest req = new PostAdminUserRemoveRequest();
        req.setHeader(parent.adminDefHeader);
        // Check for invalid value
        req.userId("123123123123312312");
        General.launch_test_request("/admin/user/remove",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNull(response.getAffectedRecords());
                                        Assert.assertNotNull(response.getError());
                                    });
        req.userId(parent.added_users.keySet()
                                     .stream()
                                     .findFirst()
                                     .get());
        General.launch_test_request("/admin/user/remove",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNotNull(response.getAffectedRecords());
                                        Assert.assertEquals(response.getAffectedRecords()
                                                                    .size(),
                                                            1);
                                        Assert.assertEquals(response.getAffectedRecords()
                                                                    .get(0),
                                                            req.getUserId());
                                    });
        parent.added_users.remove(req.getUserId());

    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Removed first user");
}

}