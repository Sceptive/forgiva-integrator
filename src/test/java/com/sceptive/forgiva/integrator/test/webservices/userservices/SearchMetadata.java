package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

public class SearchMetadata implements ITest {
@Override
public void launch(final IFTests _parent) {


    UserServicesTests parent = (UserServicesTests) _parent;


    try {

        for (String keyword : parent.added_keyword_record_counts.keySet()) {

            PostUserMetadataSearchRequest req = new PostUserMetadataSearchRequest();
            req.setHeader(parent.session_header);
            req.criteria(keyword);

            SimpleResponse sr =
                    HttpsClient.post_api("/user/metadata/search", General.omapper.writeValueAsString(req));

            Assert.assertEquals(sr.getCode(), 200);

            PostUserMetadataSearchResponse response =
                    General.omapper.readValue(sr.getBody(), PostUserMetadataSearchResponse.class);

            Assert.assertTrue(
                    response.getMetadata().size() == parent.added_keyword_record_counts.get(keyword).intValue());
        }

        /** SHOULD RESPOND WITH NO METADATA */
        PostUserMetadataSearchRequest req = new PostUserMetadataSearchRequest();
        req.setHeader(parent.session_header);
        req.criteria("invalid keyword");

        SimpleResponse sr =
                HttpsClient.post_api("/user/metadata/search", General.omapper.writeValueAsString(req));

        Assert.assertEquals(sr.getCode(), 200);

        PostUserMetadataSearchResponse response =
                General.omapper.readValue(sr.getBody(), PostUserMetadataSearchResponse.class);

        Assert.assertNull(response.getMetadata());

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}
}
