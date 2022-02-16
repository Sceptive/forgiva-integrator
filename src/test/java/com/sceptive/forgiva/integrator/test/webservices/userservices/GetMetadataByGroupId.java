package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

import static com.sceptive.forgiva.integrator.test.webservices.UserServicesTests.test_iterations;

public class GetMetadataByGroupId implements ITest {
    @Override
    public void launch(final IFTests _parent) {


        UserServicesTests parent = (UserServicesTests) _parent;

        try {
            for (Long groupId : parent.added_metadata_group_ids) {
                PostUserMetadataBygroupRequest req = new PostUserMetadataBygroupRequest();
                req.setHeader(parent.session_header);
                req.groupId(groupId.toString())
                   .startIdx(0)
                   .count(test_iterations * 2);
                SimpleResponse sr = HttpsClient.post_api("/user/metadata/by_group",
                                                         General.omapper.writeValueAsString(req));
                Assert.assertEquals(sr.getCode(),
                                    200);
                PostUserMetadataBygroupResponse or = General.omapper.readValue(sr.getBody(),
                                                                               PostUserMetadataBygroupResponse.class);
                if (or.getMetadatas() == null) {
                    // Info.get_instance().print("%s - %s", groupId.toString(), sr.getBody());
                    Assert.assertTrue(parent.added_metadata_counts_bygroupid.get(groupId) == 0);
                } else {
                    Assert.assertTrue(or.getMetadatas()
                                        .size() == parent.added_metadata_counts_bygroupid.get(groupId));
                    if (or.getTotalRecords() >= 10) {
                        int random_start = Common.random_next_int(or.getTotalRecords() / 2);
                        int random_count = (or.getTotalRecords() - random_start) / 2;
                        req.startIdx(random_start)
                           .count(random_count);
                        sr = HttpsClient.post_api("/user/metadata/by_group",
                                                  General.omapper.writeValueAsString(req));
                        PostUserMetadataBygroupResponse or2 = General.omapper.readValue(sr.getBody(),
                                                                                        PostUserMetadataBygroupResponse.class);
                        Assert.assertEquals(sr.getCode(),
                                            200);
                        Assert.assertTrue(or2.getMetadatas()
                                             .size() == random_count);
                    }
                }
                Assert.assertTrue(or.getTotalRecords() == parent.added_metadata_counts_bygroupid.get(groupId));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
