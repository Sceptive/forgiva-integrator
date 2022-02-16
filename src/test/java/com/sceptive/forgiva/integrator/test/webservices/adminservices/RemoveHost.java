package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminApplicationsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminApplicationsResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostRemoveRequest;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class RemoveHost implements ITest {

@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {

        PostAdminHostRemoveRequest req = new PostAdminHostRemoveRequest();
        req.setHeader(parent.adminDefHeader);
        // Check for invalid value
        req.hostId("123123123123312312");
        General.launch_test_request("/admin/host/remove",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNull(response.getAffectedRecords());
                                        Assert.assertNotNull(response.getError());
                                    });


        req.hostId(parent.added_hosts.keySet()
                                     .stream()
                                     .findFirst()
                                     .get());
        General.launch_test_request("/admin/host/remove",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNotNull(response.getAffectedRecords());

                                        Assert.assertEquals(response.getAffectedRecords()
                                                                    .get(0),
                                                            req.getHostId());
                                    });


        PostAdminApplicationsRequest request = new PostAdminApplicationsRequest();
        request.setHeader(parent.adminDefHeader);
        request.hostId(Integer.parseInt(parent.added_hosts.keySet()
                                                          .stream()
                                                          .findFirst()
                                                          .get()));

        General.launch_test_request("/admin/applications/by_host",
                                    request,
                                    PostAdminApplicationsResponse.class,
                                    200,
                                    (response) -> {

                                        Assert.assertNull(response.getApplications());

                                    });

        parent.added_hosts.remove(req.getHostId());
    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
    Info.get_instance().print("First host is removed");
}
}