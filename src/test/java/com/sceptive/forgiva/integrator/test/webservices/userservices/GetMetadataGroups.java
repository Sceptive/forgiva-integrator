package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

public class GetMetadataGroups implements ITest {
@Override
public void launch(final IFTests _parent) {


    UserServicesTests parent = (UserServicesTests) _parent;


    try {

        PostUserMetadataGroupsRequest req = new PostUserMetadataGroupsRequest();
        req.setHeader(parent.session_header);

        SimpleResponse sr =
                HttpsClient.post_api("/user/metadata/groups", General.omapper.writeValueAsString(req));

        Assert.assertEquals(sr.getCode(), 200);

        PostUserMetadataGroupsResponse response =
                General.omapper.readValue(sr.getBody(), PostUserMetadataGroupsResponse.class);

        Info.get_instance().print("Found %d Expected %d" ,response.getGroups().size(), parent.added_metadata_group_ids.size());
        Assert.assertTrue(response.getGroups().size() == parent.added_metadata_group_ids.size());

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

}
}
