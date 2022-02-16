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

public class RemoveMetadataGroup implements ITest {
@Override
public void launch(final IFTests _parent) {


    UserServicesTests parent = (UserServicesTests) _parent;

    try {

        PostUserMetadataGroupRemoveRequest req = new PostUserMetadataGroupRemoveRequest();
        req.setHeader(parent.session_header);

        Long group_id_to_remove     = parent.added_metadata_group_ids.elementAt(0);
        int metadata_get_removed    = parent.added_metadata_counts_bygroupid.get(group_id_to_remove);

        req.setMetadataGroupId(String.valueOf(group_id_to_remove));

        SimpleResponse sr =
                HttpsClient.post_api("/user/metadata/group/remove", General.omapper.writeValueAsString(req));

        Assert.assertEquals(sr.getCode(), 200);

        OperationResult response = General.omapper.readValue(sr.getBody(), OperationResult.class);

        Assert.assertNotNull(response.getInfo());

        int actually_removed = Integer.parseInt(response.getInfo().split(" ")[0]);
        parent.removed_by_group_membership = actually_removed;

        Info.get_instance()
            .print("Removed %d metadatas expected %d", actually_removed, metadata_get_removed);
        Assert.assertTrue(actually_removed >= metadata_get_removed);

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

}
}
