package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.MetadataGroup;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataGroupAddRequest;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

import static com.sceptive.forgiva.integrator.test.webservices.UserServicesTests.test_iterations;

public class AddMetadataGroup implements ITest {

    @Override
    public void launch(final IFTests _parent) {


        UserServicesTests parent = (UserServicesTests) _parent;



        try {

            String recent_group_id = null;


            for (int i = 0; i < test_iterations; i++) {
                boolean                         has_parent = false;
                PostUserMetadataGroupAddRequest req        = new PostUserMetadataGroupAddRequest();
                req.setHeader(parent.session_header);
                req.group(new MetadataGroup()
                                  .groupName("Test Group " + i)
                                  .groupDescription("Test Group Description of (" + i + ")"));

                if (recent_group_id != null && Common.random_boolean()) {
                    req.getGroup().parentGroupId(recent_group_id);
                    has_parent = true;
                }

                SimpleResponse sr =
                        HttpsClient.post_api("/user/metadata/group/add", General.omapper.writeValueAsString(req));

                Assert.assertEquals(sr.getCode(), 200);

                OperationResult or = General.omapper.readValue(sr.getBody(), OperationResult.class);

                Assert.assertNull(or.getError() );
                Assert.assertEquals(or.getAffectedRecords().size(), 1);

                parent.added_metadata_group_ids.add(Long.parseLong(or.getAffectedRecords().get(0)));
                if (Common.random_boolean() && !has_parent) {
                    recent_group_id = or.getAffectedRecords().get(0);
                }
            }


            // Test empty group name add
            PostUserMetadataGroupAddRequest req = new PostUserMetadataGroupAddRequest();
            req.setHeader(parent.session_header);
            req.group(new MetadataGroup());
            SimpleResponse sr =
                    HttpsClient.post_api("/user/metadata/group/add", General.omapper.writeValueAsString(req));

            Assert.assertEquals(sr.getCode(), 200);
            OperationResult or = General.omapper.readValue(sr.getBody(), OperationResult.class);
            Assert.assertTrue(or.getAffectedRecords() == null || or.getAffectedRecords().size() == 0);
            Assert.assertTrue(or.getError() != null && or.getError().length() > 0);




        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        Info.get_instance().print("Added %d new metadata groups", test_iterations);
    }

}
