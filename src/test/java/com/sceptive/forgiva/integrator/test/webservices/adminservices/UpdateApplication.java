package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class UpdateApplication implements ITest {


@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {

        PostAdminApplicationUpdateRequest req = new PostAdminApplicationUpdateRequest();
        req.setHeader(parent.adminDefHeader);
        parent.added_applications.forEach((application_id, application_name) -> {
            req.application(new Application().applicationId(Integer.parseInt(application_id))
                                             .applicationName(application_name + "_updated"));
            General.launch_test_request("/admin/application/update",
                                        req,
                                        OperationResult.class,
                                        200,
                                        (response) -> {
                                            Assert.assertNotNull(response.getAffectedRecords());
                                            Assert.assertEquals(response.getAffectedRecords()
                                                                        .get(0),
                                                                application_id);
                                        });
        });

        // Check for invalid application id
        req.application(new Application().applicationId(Integer.parseInt("2139794"))
                                         .applicationName("Will be failed"));
        General.launch_test_request("/admin/application/update",
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
    Info.get_instance().print("All applications are updated");
}
}