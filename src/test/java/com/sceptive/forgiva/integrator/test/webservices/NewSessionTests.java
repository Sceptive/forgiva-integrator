package com.sceptive.forgiva.integrator.test.webservices;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.crypto.AsymmetricKeyPair;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.Header;
import com.sceptive.forgiva.integrator.services.gen.model.PostNewSessionRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostNewSessionResponse;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.IFTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(dependsOnGroups = "crypto")
public class NewSessionTests extends IFTests {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        General.setup_web_services();;
    }

    @Test(testName = "New Session",
            description = "Testing new session generation operations.",
            priority = 0, groups = { "new-session-tests", "web-services-tests" })
    public void test_new_session() {

        PostNewSessionRequest pns_r = new PostNewSessionRequest();
        AsymmetricKeyPair client_kp = General.get_client_asymmetric_keys();
        pns_r.setClientPk(Common.encodeHex(client_kp.getPublicKey()));
        try {

            // ************************************************************ //
            // Checking if web service providing right values
            // ************************************************************ //
            Info.get_instance().print("Checking if web service providing right values");
            String response = HttpsClient.post_api("/new_session",
                                                   General.omapper.writeValueAsString(pns_r)).getBody();

            Assert.assertNotNull(response);

            PostNewSessionResponse obj_response = General.omapper.readValue(response, PostNewSessionResponse.class);

            Assert.assertEquals(Configuration.runtime.security_default_hashing_salt,    obj_response.getHshSalt());
            Assert.assertEquals(Configuration.runtime.security_default_hashing,         obj_response.getHshAlg());
            Assert.assertNotNull(obj_response.getNewSessionId());

            // ******************************************************************** //
            // Checking if session id is validated and not a new session id is given
            // ******************************************************************** //
            Info.get_instance().print("Checking if session id is validated and not a new session id is given");
            pns_r.setHeader(new Header()
                    .sessionId(obj_response.getNewSessionId())
            );


            response = HttpsClient.post_api("/new_session",
                                            General.omapper.writeValueAsString(pns_r)).getBody();
            Assert.assertNotNull(response);
            obj_response = General.omapper.readValue(response, PostNewSessionResponse.class);

            Assert.assertNull(obj_response.getNewSessionId());




        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }


}
