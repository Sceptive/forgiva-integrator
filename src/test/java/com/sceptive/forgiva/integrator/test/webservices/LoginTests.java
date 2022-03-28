package com.sceptive.forgiva.integrator.test.webservices;

import com.sceptive.forgiva.integrator.core.crypto.AsymmetricKeyPair;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(dependsOnGroups = "new-session-tests")
public class LoginTests extends IFTests {
@BeforeClass(alwaysRun = true)
public void setup() {
    General.setup_web_services();
    ;
}

@Test(testName = "Login",
      description = "Testing login operations.",
      groups = {"login-tests", "web-services-tests"},
      priority = 1)
public void test_login() {
    try {
        AsymmetricKeyPair client_kps = General.get_client_asymmetric_keys();
        // ************************************************************ //
        // Getting a new session id
        // ************************************************************ //
        Info.get_instance()
            .print("Getting a new session id");
        PostNewSessionResponse p_nsr = General.get_new_session(client_kps.getPublicKey());
        // ************************************************************ //
        // Trying Administrator login
        // ************************************************************ //
        Info.get_instance()
            .print("Trying Administrator login");
        PostLoginResponse p_lr = General.login(client_kps,
                                               "marcus",
                                               "development",
                                               p_nsr);
        // Should get logged in
        Assert.assertTrue(p_lr.getLogonState()!= null && p_lr.getLogonState()
                                                             .getAuthenticated());
        Assert.assertTrue(p_lr.getLogonState()!= null && p_lr.getLogonState().getIsAdmin());

        // ************************************************************ //
        // Invalidating/logging out the administrator session
        // ************************************************************ //
        Info.get_instance()
            .print("Invalidating/logging out the administrator session");
        General.logout(p_nsr);
        // ************************************************************ //
        // Being sure prior session is invalidated
        // ************************************************************ //
        Info.get_instance()
            .print("Being sure prior session is invalidated");
        PostNewSessionRequest __p_nsr = new PostNewSessionRequest().header(new Header().sessionId(p_nsr.getNewSessionId()))
                                                                   .clientPk(Common.encodeHex(client_kps.getPublicKey()));
        String response = HttpsClient.post_api("/new_session",
                                               General.omapper.writeValueAsString(__p_nsr))
                                     .getBody();
        PostNewSessionResponse p_newsessionresponse = General.omapper.readValue(response,
                                                                                PostNewSessionResponse.class);
        Assert.assertNotNull(p_newsessionresponse.getNewSessionId());
        // ************************************************************ //
        // Trying Administrator login with invalid credentials
        // ************************************************************ //
        Info.get_instance()
            .print("Trying Administrator login with invalid credentials");
        PostLoginResponse p_lr2 = General.login(client_kps,
                                                "marcus",
                                                "under_development",
                                                p_nsr);
        // Should actually be null because we are using invalidated sessionId
        Assert.assertNull(p_lr2);
        p_nsr = General.get_new_session(client_kps.getPublicKey());
        p_lr2 = General.login(client_kps,
                              "marcus",
                              "under_development",
                              p_nsr);
        Assert.assertNotNull(p_lr2);
        // Should not get logged in
        Assert.assertFalse(p_lr2.getLogonState() != null && p_lr2.getLogonState().getAuthenticated());
        Assert.assertFalse(p_lr2.getLogonState() != null && p_lr2.getLogonState().getIsAdmin());
        General.logout(p_nsr);
        // ************************************************************ //
        // Trying login for an ordinary user
        // ************************************************************ //
        Info.get_instance()
            .print("Trying login for an ordinary user");
        General.add_user("test_user",
                         "development");
        p_nsr = General.get_new_session(client_kps.getPublicKey());
        PostLoginResponse p_lr3 = General.login(client_kps,
                                                "test_user",
                                                "development",
                                                p_nsr);
        Assert.assertNotNull(p_lr3);
        // Should get logged in
        Assert.assertTrue(p_lr3.getLogonState() != null &&  p_lr3.getLogonState().getAuthenticated());
        Assert.assertFalse(p_lr3.getLogonState() != null && p_lr3.getLogonState().getIsAdmin());

        // ************************************************************ //
        // Enabling 2FA
        // ************************************************************ //
        String sotp_code = General.enable_2fa(client_kps,p_nsr);

        Info.get_instance()
                        .print("Enabling 2FA for %s : %s",p_nsr.getNewSessionId(), sotp_code);

        Assert.assertNotNull(sotp_code);
        General.logout(p_nsr);

        // Trying to re-login
        p_nsr = General.get_new_session(client_kps.getPublicKey());
        PostLoginResponse p_lr4 = General.login(client_kps,
                "test_user",
                "development",
                p_nsr);
        Assert.assertNotNull(p_lr4);
        // Should not get authenticated and demanding 2FA login
        Assert.assertTrue(p_lr4.getLogonState() != null &&  !p_lr4.getLogonState().getAuthenticated());
        Assert.assertTrue(p_lr4.getLogonState() != null && p_lr4.getTwoFARequired());

        // Login 2FA
        PostLogin2faResponse p_2faresp = General.login2fa(client_kps, p_nsr, sotp_code);
        Assert.assertNotNull(p_2faresp);

        Info.get_instance()
                .print("Logging in with 2FA for %s : %s",p_nsr.getNewSessionId(),sotp_code);


        // ************************************************************ //
        // Disabling 2FA
        // ************************************************************ //
        General.disable_2fa(client_kps,p_nsr,sotp_code);

        Info.get_instance()
                .print("Disabling 2FA for %s : %s",p_nsr.getNewSessionId(),sotp_code);


        General.logout(p_nsr);

        // Re logging in without requiring 2FA
        // Trying to re-login
        p_nsr = General.get_new_session(client_kps.getPublicKey());
        PostLoginResponse p_lr5 = General.login(client_kps,
                "test_user",
                "development",
                p_nsr);
        Assert.assertNotNull(p_lr5);
        // Should not get authenticated and demanding 2FA login
        Assert.assertTrue(p_lr5.getLogonState() != null &&  p_lr5.getLogonState().getAuthenticated());
        Assert.assertTrue(p_lr5.getLogonState() != null && !p_lr5.getTwoFARequired());

    }
    catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}
}
