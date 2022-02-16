package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.AsymmetricKeyPair;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.Crypto;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminUserAddRequest;
import com.sceptive.forgiva.integrator.services.gen.model.User;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

public class AddUser implements ITest {
@Override
public void launch(final IFTests _parent) throws Exception {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {
        String last_user = null;
        String last_email = null;
        String last_password = null;
        for (String group_id : parent.added_groups.keySet()) {
            int amount = Common.random_next_int(parent.test_iterations);
            for (int i = 0; i < amount; i++) {
                PostAdminUserAddRequest req = new PostAdminUserAddRequest();
                req.setHeader(parent.adminDefHeader);
                final String user = "user_" + i + "_" + group_id;
                final String password = Common.generate_password(16);
                final byte[] session_pk = Common.decodeHex(parent.pnsr_response.getSessionPk());
                final byte[] session_hash_salt = Common.decodeHex(parent.pnsr_response.getHshSalt());
                final byte[] password_hash = Crypto.digest_or_kdf(parent.pnsr_response.getHshAlg(),
                                                                  password.getBytes(),
                                                                  session_hash_salt,
                                                                  null);
                final AsymmetricKeyPair client_encrypt_kp = Asymmetric.init_cipherkeypair_bytes(session_pk,
                                                                                                parent.client_kp.getPrivateKey());
                final byte[] encrypted_password_hash = Asymmetric.encrypt_using_keypair(password_hash,
                                                                                        client_encrypt_kp);
                final String e_password = Common.encodeHex(encrypted_password_hash);
                req.password(e_password);
                req.userGroupId(Integer.parseInt(group_id));
                req.user(new User().userName(user)
                                   .fullName("User " + i)
                                   .email("abc" + i + "-" + group_id + "@def.com"));
                General.launch_test_request("/admin/user/add",
                                            req,
                                            OperationResult.class,
                                            200,
                                            (response) -> {
                                                Assert.assertEquals(response.getAffectedRecords()
                                                                            .size(),
                                                                    1);
                                                parent.added_users.put(response.getAffectedRecords()
                                                                               .get(0),
                                                                       user);
                                            });
                last_user = user;
                last_email = req.getUser()
                                .getEmail();
                last_password = e_password;

            }
            parent.added_user_counts_by_group.put(group_id,
                                                  amount);
            // Very CPU centric operation (depending on hash model) so to take tests took
            // a proper amount of time we break after test_iterations
            if (parent.added_users.size() > parent.test_iterations)
                break;
        }
        // Check if it accepts with same user or same email
        PostAdminUserAddRequest req = new PostAdminUserAddRequest();
        req.setHeader(parent.adminDefHeader);
        req.user(new User().userName(last_user)
                           .email("test_duplicate_username"));
        req.password(last_password);
        General.launch_test_request("/admin/user/add",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNull(response.getAffectedRecords());
                                        Assert.assertNotNull(response.getError());
                                        Assert.assertTrue(response.getError()
                                                                  .length() > 0);
                                    });
        req.user(new User().userName("test_duplicate_email")
                           .email(last_email));
        General.launch_test_request("/admin/user/add",
                                    req,
                                    OperationResult.class,
                                    200,
                                    (response) -> {
                                        Assert.assertNull(response.getAffectedRecords());
                                        Assert.assertNotNull(response.getError());
                                        Assert.assertTrue(response.getError()
                                                                  .length() > 0);
                                    });

    } catch (Exception e) {

        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
    Info.get_instance()
        .print("Added %d new users",
               parent.added_users.size());
}
}