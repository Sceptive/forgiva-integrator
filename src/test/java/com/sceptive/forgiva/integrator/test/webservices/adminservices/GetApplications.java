package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class GetApplications implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {

        PostAdminApplicationsRequest request = new PostAdminApplicationsRequest();
        request.setHeader(parent.adminDefHeader);
        General.launch_test_request("/admin/applications/by_host",
                                    request,
                                    PostAdminApplicationsResponse.class,
                                    200,
                                    (resp) -> {
                                        Assert.assertNotNull(resp.getApplications());
                                        Info.get_instance()
                                            .print("Received %d applications",
                                                   resp.getApplications()
                                                       .size());
                                    }
                                   );

    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Get %d applications", parent.added_applications.size());
}
}