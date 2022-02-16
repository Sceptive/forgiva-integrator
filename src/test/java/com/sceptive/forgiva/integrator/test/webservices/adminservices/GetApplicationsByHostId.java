package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class GetApplicationsByHostId implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {

        PostAdminApplicationsRequest request = new PostAdminApplicationsRequest();
        request.setHeader(parent.adminDefHeader);
        String h_id = parent.added_hosts.keySet()
                                        .stream()
                                        .findFirst()
                                        .get();

        request.hostId(Integer.parseInt(h_id));
        General.launch_test_request("/admin/applications/by_host",
                                    request,
                                    PostAdminApplicationsResponse.class,
                                    200,

                                    (resp) -> {

                                        if (resp.getApplications().size() > 0) {

                                            Info.get_instance()
                                                .print("Received %d applications",
                                                       resp.getApplications()
                                                           .size());

                                            Assert.assertNotNull(resp.getApplications());

                                            for (Application a : resp.getApplications()) {

                                                String application_name = String.valueOf(parent.added_applications.get(a.getHostId()));
                                                Assert.assertEquals(h_id,(String.valueOf(a.getHostId())));
                                                Assert.assertNotNull(application_name);
                                            }

                                        }
                                    });

    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Get %d applications", parent.added_applications.size());
}
}
