package com.sceptive.forgiva.integrator.test.webservices.adminservices;


import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.Host;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostAddRequest;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class AddHost implements ITest {

@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {
        final String[] recent_host_id = {null};
        String recent_hostName = null;
        String recent_dnsName = null;

        for (int i = 0; i < parent.test_iterations; i++) {
            PostAdminHostAddRequest request = new PostAdminHostAddRequest();
            request.setHeader(parent.adminDefHeader);
            request.host(new Host().hostName("host" + i + ".com")
                                   .dnsName("10.0.0." +i)
                                   .description("Host Description " + i));
            if (recent_host_id[0] != null && Common.random_boolean()) {
                request.getHost()
                       .hostId(Integer.parseInt(recent_host_id[0]));
            }

            General.launch_test_request("/admin/host/add",
                                        request,
                                        OperationResult.class,
                                        200,
                                        (response) -> {
                                            Assert.assertNotNull(response.getAffectedRecords());
                                            Assert.assertEquals(response.getAffectedRecords()
                                                                        .size(),
                                                                1);
                                            parent.added_hosts.put(response.getAffectedRecords()
                                                                           .get(0),
                                                                   request.getHost()
                                                                          .getHostName());
                                            if (Common.random_boolean()) {
                                                recent_host_id[0] = response.getAffectedRecords()
                                                                            .get(0);
                                            }
                                        });

            recent_hostName = request.getHost().getHostName();
            recent_dnsName = request.getHost().getDnsName();

        }

        PostAdminHostAddRequest req = new PostAdminHostAddRequest();
        req.setHeader(parent.adminDefHeader);
        req.host(new Host().hostName(recent_hostName)
                           .dnsName(recent_dnsName));



        General.launch_test_request("/admin/application/add",
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

    Info.get_instance().print("Added %d new hosts", parent.test_iterations);
}

}