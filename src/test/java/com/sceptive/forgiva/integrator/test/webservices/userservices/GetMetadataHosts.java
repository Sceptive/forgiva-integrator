package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

import static com.sceptive.forgiva.integrator.test.webservices.UserServicesTests.test_hosts;

public class GetMetadataHosts implements ITest {
@Override
public void launch(final IFTests _parent) {


    UserServicesTests parent = (UserServicesTests) _parent;


    try {

        PostUserMetadataHostRequest req = new PostUserMetadataHostRequest();
        req.setHeader(parent.session_header);
        req.setFilter("");

        SimpleResponse sr =
                HttpsClient.post_api("/user/metadata/host", General.omapper.writeValueAsString(req));

        Assert.assertEquals(sr.getCode(), 200);

        PostUserMetadataHostResponse response =
                General.omapper.readValue(sr.getBody(), PostUserMetadataHostResponse.class);

        Assert.assertTrue(response.getHosts().size() == test_hosts.length);

        req.setFilter(response.getHosts().get(0).getHost());
        sr = HttpsClient.post_api("/user/metadata/host", General.omapper.writeValueAsString(req));

        Assert.assertEquals(sr.getCode(), 200);
        response = General.omapper.readValue(sr.getBody(), PostUserMetadataHostResponse.class);
        Assert.assertTrue(response.getHosts().size() == 1);

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

}
}
