package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataRemoveRequest;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

public class RemoveMetadata implements ITest {
@Override
public void launch(final IFTests _parent) {


    UserServicesTests parent = (UserServicesTests) _parent;

    try {

        int already_removed = 0;
        int removed = 0;

        for (Long id : parent.added_metadata_ids) {

            PostUserMetadataRemoveRequest req = new PostUserMetadataRemoveRequest();
            req.setHeader(parent.session_header);
            req.setMetadataId(id.toString());

            SimpleResponse sr =
                    HttpsClient.post_api("/user/metadata/remove", General.omapper.writeValueAsString(req));

            Assert.assertEquals(sr.getCode(), 200);

            OperationResult response = General.omapper.readValue(sr.getBody(), OperationResult.class);

            if (response.getError() != null && response.getError().length() > 0) {
                already_removed++;
            } else {
                Assert.assertTrue(response.getAffectedRecords().size() == 1);
                Assert.assertTrue(response.getAffectedRecords().get(0).equalsIgnoreCase(id.toString()));
                removed++;
            }
        }

        Assert.assertTrue(already_removed == parent.removed_by_group_membership);

        Info.get_instance().print("%s records not removed but %s are so.", already_removed, removed);

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

}
}
