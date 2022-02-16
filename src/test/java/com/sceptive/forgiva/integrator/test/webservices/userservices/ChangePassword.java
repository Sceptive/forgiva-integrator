package com.sceptive.forgiva.integrator.test.webservices.userservices;

import com.sceptive.forgiva.integrator.core.PasswordGuard;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.AsymmetricKeyPair;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

import java.util.concurrent.atomic.AtomicReference;

public class ChangePassword implements ITest {



    private String change_user_password(Header                 _session_header,
                                      PostNewSessionResponse _ps_response,
                                      PostLoginResponse      _pl_response,
                                      String                 _username,
                                      String                 _initial_password,
                                      AsymmetricKeyPair      _client_kp
                                      ) throws Exception {

        String server_public_key    = _ps_response.getSessionPk();
        String client_private_key   = Common.encodeHex(_client_kp.getPrivateKey());


        String e_old_password       = Common.encodeHex(Asymmetric.encrypt_using_keypair(_initial_password.getBytes(),
                                                                                Asymmetric.init_cipherkeypair(server_public_key,
                                                                                                            client_private_key)));

        String new_password         = Common.generate_password(16);

        String e_new_password       = Common.encodeHex(Asymmetric.encrypt_using_keypair(new_password.getBytes(),
                                                                                Asymmetric.init_cipherkeypair(server_public_key,
                                                                                                             client_private_key)));


        PostUserPasswordChangeRequest   _pc_request = new PostUserPasswordChangeRequest().header(_session_header);

        _pc_request.setOldPassword(e_old_password);
        _pc_request.setNewPassword(e_new_password);


        if (PasswordGuard.get_instance().isGenerating_hashset()) {
            Info.get_instance().print("Waiting for Password Guard to launch..");

        }

        while (PasswordGuard.get_instance().isGenerating_hashset()) {
            try {
                Thread.sleep(100);

            }
            catch (InterruptedException _e) {
                Warning.get_instance().print(_e);
            }
        }

        SimpleResponse sr =
                HttpsClient.post_api("/user/password/change", General.omapper.writeValueAsString(_pc_request));

        Assert.assertEquals(sr.getCode(), 200);

        OperationResult or = General.omapper.readValue(sr.getBody(), OperationResult.class);

        Assert.assertNull(or.getError());

        return new_password;
    }


    @Override
    public void launch(final IFTests _parent) {

        try {

            String initial_password = Common.generate_password(16);
            String user_name        = "change_password_test_user";


            General.create_and_login_as((ps_response, pl_response, header, client_pk) -> {

                String new_password = change_user_password(header,ps_response,pl_response,user_name,initial_password,client_pk);

                General.login_as((ps_response1, pl_response1, header1, client_kp) -> {

                    Info.get_instance().print("Successfully changed user password");

                    General.remove_user(user_name);

                }, user_name, new_password);

            }, user_name, initial_password);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }
}