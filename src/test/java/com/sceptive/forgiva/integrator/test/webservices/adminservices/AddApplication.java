package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class AddApplication implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {

        String last_application_name = null;
        long last_hostId = 0;


        for (String host_id : parent.added_hosts.keySet()) {
            int amount = Common.random_next_int(parent.test_iterations);

            for (int i = 0; i < amount; i++) {
                PostAdminApplicationAddRequest request = new PostAdminApplicationAddRequest();
                request.setHeader(parent.adminDefHeader);
                request.application(new Application().applicationName("Application " + i)
                                                     .hostId(Integer.parseInt(host_id)));

                General.launch_test_request("/admin/application/add",
                                            request,
                                            OperationResult.class,
                                            200,
                                            (response) -> {
                                                Assert.assertEquals(response.getAffectedRecords()
                                                                            .size(),
                                                                    1);
                                                parent.added_applications.put(response.getAffectedRecords()
                                                                                      .get(0),
                                                                              Long.parseLong(host_id));
                                            });

                last_application_name = request.getApplication().getApplicationName();
                last_hostId = request.getApplication().getHostId();


                parent.added_application_counts_by_host.put(host_id,
                                                            amount);

                if (parent.added_applications.size() > parent.test_iterations)
                    break;
            }
            // Check if it accepts with same application name on the same host IP
            PostAdminApplicationAddRequest req = new PostAdminApplicationAddRequest();
            req.setHeader(parent.adminDefHeader);
            req.application(new Application().applicationName(last_application_name)
                                             .hostId((int) last_hostId));

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
        }

    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Added %d new application", parent.added_applications.size());
}

}