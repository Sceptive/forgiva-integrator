package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.Host;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostsRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminHostsResponse;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class GetHosts implements ITest {
@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {

        PostAdminHostsRequest request = new PostAdminHostsRequest();
        request.setHeader(parent.adminDefHeader);
        General.launch_test_request("/admin/hosts",
                                    request,
                                    PostAdminHostsResponse.class,
                                    200,
                                    (resp) -> {
                                        Assert.assertNotNull(resp.getHosts());
                                        Info.get_instance()
                                            .print("Received %d hosts",
                                                   resp.getHosts()
                                                       .size());
                                        for (Host h : resp.getHosts()) {
                                            String host_name = parent.added_hosts.get(String.valueOf(h.getHostId()));
                                            Assert.assertNotNull(host_name);
                                            Assert.assertTrue(host_name.equals(h.getHostName()));
                                        }
                                    }
                                   );

    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    Info.get_instance().print("Get %d host", parent.added_hosts.size());
}
}
