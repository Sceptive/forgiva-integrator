package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.Metadata;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserMetadataAddRequest;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

import static com.sceptive.forgiva.integrator.test.webservices.UserServicesTests.test_accounts;
import static com.sceptive.forgiva.integrator.test.webservices.UserServicesTests.test_hosts;

public class AddMetadata implements ITest {

@Override
public void launch(final IFTests _parent) {


    UserServicesTests parent = (UserServicesTests) _parent;

    try {

        for (Long group_id : parent.added_metadata_group_ids) {

            int amount = Common.random_next_int(UserServicesTests.test_iterations);
            for (int i = 0; i < amount; i++) {

                String host_to_add = UserServicesTests.test_hosts[Common.random_next_int(test_hosts.length)];
                String                     account_to_add = test_accounts[Common.random_next_int(test_accounts.length)];
                PostUserMetadataAddRequest req            = new PostUserMetadataAddRequest();
                Metadata md =
                        new Metadata()
                                .groupId(group_id.toString())
                                .host(host_to_add)
                                .account(account_to_add)
                                .generatedBefore(false)
                                .complexity(1);
                req.metadata(md)
                   .passwordLength(32)
                   .optLowercase(true)
                   .optUppercase(true)
                   .optSymbols(true)
                   .optNumbers(true);
                req.setHeader(parent.session_header);

                SimpleResponse sr =
                        HttpsClient.post_api("/user/metadata/add", General.omapper.writeValueAsString(req));

                Assert.assertEquals(sr.getCode(), 200);

                OperationResult or = General.omapper.readValue(sr.getBody(), OperationResult.class);

                Assert.assertEquals(or.getAffectedRecords().size(), 1);

                md.metadataId(or.getAffectedRecords().get(0));

                Long md_id = Long.parseLong(or.getAffectedRecords().get(0));
                parent.added_metadata_ids.add(md_id);
                parent.added_metadatass.put(md_id, md);

                UserServicesTests.increase(parent.added_keyword_record_counts, host_to_add);
                UserServicesTests.increase(parent.added_keyword_record_counts, account_to_add);
            }

            parent.added_metadata_counts_bygroupid.put(group_id, amount);
        }

        Info.get_instance().print("Added %d total new metadatas", parent.added_metadata_ids.size());

        /** SHOULD FAIL (WITHOUT METADATA) */
        PostUserMetadataAddRequest req = new PostUserMetadataAddRequest();
        req.passwordLength(32)
           .optLowercase(true)
           .optUppercase(true)
           .optSymbols(true)
           .optNumbers(true);
        req.setHeader(parent.session_header);
        SimpleResponse sr =
                HttpsClient.post_api("/user/metadata/add", General.omapper.writeValueAsString(req));

        OperationResult or = General.omapper.readValue(sr.getBody(), OperationResult.class);

        Assert.assertNotNull(or.getError());
        Assert.assertTrue(or.getError().length() > 0);

        /** SHOULD FAIL (WITHOUT HOST AND ACCOUNT) */
        req = new PostUserMetadataAddRequest();
        req.metadata(new Metadata().complexity(3))
           .passwordLength(32)
           .optLowercase(true)
           .optUppercase(true)
           .optSymbols(true)
           .optNumbers(true);
        req.setHeader(parent.session_header);
        sr = HttpsClient.post_api("/user/metadata/add", General.omapper.writeValueAsString(req));

        or = General.omapper.readValue(sr.getBody(), OperationResult.class);

        Assert.assertNotNull(or.getError());
        Assert.assertTrue(or.getError().length() > 0);

        /** SHOULD FAIL (WITHOUT OptUpperCase) */
        req = new PostUserMetadataAddRequest();
        req.metadata(new Metadata().complexity(3).host("test_host").account("test_account"))
           .passwordLength(32)
           .optLowercase(true)
           .optSymbols(true)
           .optNumbers(true);
        req.setHeader(parent.session_header);
        sr = HttpsClient.post_api("/user/metadata/add", General.omapper.writeValueAsString(req));

        or = General.omapper.readValue(sr.getBody(), OperationResult.class);

        Assert.assertNotNull(or.getError());
        Assert.assertTrue(or.getError().length() > 0);

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail();
        e.getMessage();
    }

}
}
