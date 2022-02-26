package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import com.sceptive.forgiva.integrator.test.webservices.UserServicesTests;
import org.testng.Assert;

import static com.sceptive.forgiva.integrator.test.webservices.UserServicesTests.*;
import static com.sceptive.forgiva.integrator.test.webservices.UserServicesTests.test_accounts;

public class Backup implements ITest {

    @Override
    public void launch(final IFTests _parent) {

        try {

            UserServicesTests parent = (UserServicesTests) _parent;
            SimpleResponse sr;
            OperationResult or;

            Info.get_instance().print("Adding sample data..");


            for (int i = 0; i < test_iterations; i++) {

                PostUserMetadataGroupAddRequest req = new PostUserMetadataGroupAddRequest();
                req.setHeader(parent.session_header);
                req.group(new MetadataGroup()
                        .groupName("Test Group " + i)
                        .groupDescription("Test Group Description of (" + i + ")"));


                sr = HttpsClient.post_api("/user/metadata/group/add",
                        General.omapper.writeValueAsString(req));

                Assert.assertEquals(sr.getCode(), 200);

                or = General.omapper.readValue(sr.getBody(), OperationResult.class);

                Assert.assertNull(or.getError());
                Assert.assertEquals(or.getAffectedRecords().size(), 1);

                String added_group_id = or.getAffectedRecords().get(0);

                for (int ii = 0; ii < test_iterations; ii++) {
                    String host_to_add = UserServicesTests.test_hosts[Common.random_next_int(test_hosts.length)];
                    String                     account_to_add = test_accounts[Common.random_next_int(test_accounts.length)];
                    PostUserMetadataAddRequest req_am         = new PostUserMetadataAddRequest();
                    Metadata md =
                            new Metadata()
                                    .groupId(added_group_id)
                                    .host(host_to_add)
                                    .account(account_to_add)
                                    .generatedBefore(false)
                                    .complexity(1);
                    req_am.metadata(md)
                            .passwordLength(32)
                            .optLowercase(true)
                            .optUppercase(true)
                            .optSymbols(true)
                            .optNumbers(true);
                    req_am.setHeader(parent.session_header);

                    sr =
                            HttpsClient.post_api("/user/metadata/add", General.omapper.writeValueAsString(req_am));

                    Assert.assertEquals(sr.getCode(), 200);

                    or = General.omapper.readValue(sr.getBody(), OperationResult.class);

                    Assert.assertEquals(or.getAffectedRecords().size(), 1);

                    md.metadataId(or.getAffectedRecords().get(0));
                }


            }

            PostUserBackupExportRequest req_am         = new PostUserBackupExportRequest();
            req_am.setHeader(parent.session_header);

            sr =
                    HttpsClient.post_api("/user/backup/export", General.omapper.writeValueAsString(req_am));

            or = General.omapper.readValue(sr.getBody(), OperationResult.class);
            Assert.assertEquals(sr.getCode(), 200);

            Assert.assertNotNull(or.getResultData());
            Assert.assertTrue(or.getResultData().length() > 0);

            Info.get_instance().print("Exported backup with size of %d bytes for %d records ",
                    or.getResultData().length() , (test_iterations*test_iterations));


            PostUserBackupImportRequest req_im         = new PostUserBackupImportRequest();
            req_im.setHeader(parent.session_header);
            req_im.setData(or.getResultData());

            sr =
                    HttpsClient.post_api("/user/backup/import", General.omapper.writeValueAsString(req_im));

            or = General.omapper.readValue(sr.getBody(), OperationResult.class);
            Assert.assertEquals(sr.getCode(), 200);

            // Ensure that equal or more amount of records imports with created
            Assert.assertTrue(or.getAffectedRecords().size() >= (test_iterations*test_iterations) );

            Info.get_instance().print("Imported %d records over import",or.getAffectedRecords().size());


        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            e.getMessage();
        }
    }

}
