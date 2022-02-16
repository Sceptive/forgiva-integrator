package com.sceptive.forgiva.integrator.test.webservices;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sceptive.forgiva.integrator.Main;
import com.sceptive.forgiva.integrator.core.BootStrapper;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.crypto.*;
import com.sceptive.forgiva.integrator.core.db.objects.EUser;
import com.sceptive.forgiva.integrator.logging.Debug;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.test.HttpsClient;
import com.sceptive.forgiva.integrator.test.SimpleResponse;
import org.testng.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class General {
private static final String       test_username         = "test_user_services";
private static final String       test_password         = Common.generate_password(32);
public static        String       https_url_header      = "https://localhost:";
public static        ObjectMapper omapper;
private static       boolean      web_services_launched = false;

static {
    omapper = new ObjectMapper();
    omapper.disable(MapperFeature.USE_ANNOTATIONS);
}

public static <T> void launch_test_request(String _service_path, Object _request, Class<T> _response_class,
                                    int _expected_response_code, Consumer<T> _check_response) {
    try {
        SimpleResponse sr = HttpsClient.post_api(_service_path,
                                                 General.omapper.writeValueAsString(_request));
        T ret = null;
        try {
            ret = General.omapper.readValue(sr.getBody(),
                                            _response_class);
        }
        catch (Exception ignored) {
        }
        Assert.assertEquals(sr.getCode(),
                            _expected_response_code);
        if (ret != null) {
            _check_response.accept((T) ret);
        } else {
            _check_response.accept(null);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }
}


/**
 * Logs in as _user with _password and sets values on UserLoginEvent (_event) interface.
 *
 * @param _event    Interface to hook up on login
 * @param _client_kp Keypairs to use in asymmetric encryption
 * @param _user     User name
 * @param _password Password for the user
 * @throws Exception
 */
static void login_as_ex(UserLoginEvent _event, AsymmetricKeyPair _client_kp, String _user, String _password) throws Exception {



    PostNewSessionResponse sessionResponse  = General.get_new_session(_client_kp.getPublicKey());
    PostLoginResponse loginResponse         = General.login(_client_kp,
                                                    _user,
                                                    _password,
                                                    sessionResponse);

    Assert.assertTrue(loginResponse.getLogonState() != null && loginResponse.getLogonState().getAuthenticated());
    Header session_header = new Header().sessionId(sessionResponse.getNewSessionId())
                                   .clientAgent("Unit testing agent");
    _event.logged_in(sessionResponse,
                     loginResponse,
                     session_header,
                     _client_kp);
}


/**
 * Logs in as _user with _password and sets values on UserLoginEvent (_event) interface.
 *
 * @param _event    Interface to hook up on login
 * @param _user     User name
 * @param _password Password for the user
 * @throws Exception
 */
public static void login_as(UserLoginEvent _event,  String _user, String _password) throws Exception {

    AsymmetricKeyPair client_kp = get_client_asymmetric_keys();

    login_as_ex(_event, client_kp, _user, _password);
}


public static void create_and_login_as(UserLoginEvent _event, String _user, String _password) throws Exception {

    add_user(_user,
             _password);
    login_as(_event,
             _user,
             _password);
}


/**
 * Creates test user with password and hooks up UserLoginEvent object's necessary functions
 *
 * @param _event
 * @throws Exception
 */
public static void create_and_login_as_test_user(UserLoginEvent _event) throws Exception {

    create_and_login_as(_event,test_username,test_password);

}

/**
 * Sets up Web Services just for testing purposes
 */
static void setup_web_services() {
    if (!web_services_launched) {
        BootStrapper.initialize_for_testing(true,
                                            true,
                                            true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.run();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // Waiting for services to launch
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        web_services_launched = true;
    }
}

/*
    Returns random generated key pairs
 */
static AsymmetricKeyPair    get_client_asymmetric_keys() {
    return Asymmetric.generate_asymmetric_key_pairs();
}

/**
 * Returns session initiation response
 *
 * @param _client_pk Client public key
 * @return
 * @throws Exception
 */
static PostNewSessionResponse get_new_session(byte[] _client_pk) throws Exception {
    PostNewSessionRequest pns_r = new PostNewSessionRequest();
    pns_r.setClientPk(Common.encodeHex(_client_pk));

    String response = HttpsClient.post_api("/new_session",
                                           General.omapper.writeValueAsString(pns_r))
                                 .getBody();
    PostNewSessionResponse obj_response = General.omapper.readValue(response,
                                                                    PostNewSessionResponse.class);
    Debug.get_instance()
         .print("Encryption pk: %s",
                obj_response.getSessionPk());
    String session_id         = obj_response.getNewSessionId();
    byte[] session_public_key = Common.decodeHex(obj_response.getSessionPk());
    String salt               = obj_response.getHshSalt();
    String default_hashing    = obj_response.getHshAlg();
    Assert.assertNotNull(session_id);
    Assert.assertNotNull(salt);
    Assert.assertNotNull(default_hashing);
    return obj_response;
}

/**
 * Logs in to Integrator and returns the result with API object
 *
 * @param _client_kp Client key pair
 * @param _username
 * @param _password
 * @param _p_nsr
 * @return
 * @throws Exception
 */
static PostLoginResponse login(AsymmetricKeyPair _client_kp,
                               String _username,
                               String _password,
                               PostNewSessionResponse _p_nsr)
        throws Exception {


    String server_public_key = _p_nsr.getSessionPk();
    String client_private_key = Common.encodeHex(_client_kp.getPrivateKey());


    String e_username = Common.encodeHex(Asymmetric.encrypt_using_keypair(_username.getBytes(),
                                                                          Asymmetric.init_cipherkeypair(server_public_key,
                                                                                                        client_private_key)));
    String e_password =
            Common.encodeHex(Asymmetric.encrypt_using_keypair(Crypto.digest_or_kdf(_p_nsr.getHshAlg(),
                                                                                      _password.getBytes(),
                                                                                      Common.decodeHex(_p_nsr.getHshSalt()),
                                                                                      null),
                                                              Asymmetric.init_cipherkeypair(server_public_key,client_private_key)));

//    Info.get_instance().print("Encode servPk: %s cliePrvK: %s ",server_public_key, client_private_key);
//    Info.get_instance().print("Info: %s %s ",e_username, e_password);

    PostLoginRequest plr =
            new PostLoginRequest().header(new Header().sessionId(_p_nsr.getNewSessionId()))
                                  .username(e_username)
                                  .password(e_password)
            ;
    SimpleResponse sr = HttpsClient.post_api("/login",
                                             General.omapper.writeValueAsString(plr));
    String response = sr.getBody();
    Assert.assertNotNull(response);
    PostLoginResponse plresponse = response.isEmpty() ? null : General.omapper.readValue(response,
                                                                                         PostLoginResponse.class);
    return plresponse;
}

/**
 * Logs out specific session
 *
 * @param _p_nsr
 * @throws Exception
 */
static void logout(PostNewSessionResponse _p_nsr) throws Exception {
    HttpsClient.post_api("/logout",
                         General.omapper.writeValueAsString(new PostLogoutRequest().header(new Header().sessionId(_p_nsr.getNewSessionId()))));
}



public static void remove_user(String _username) throws Exception {

    EntityManager em = Database.get_instance()
                               .getEm();
    Query query = em.createQuery(
            "DELETE FROM EUser m WHERE m.username = :username ")
                    .setParameter("username", _username);
    em.getTransaction().begin();
    query.executeUpdate();
    em.getTransaction().commit();
    em.close();
}


/**
 * Adds a new user to the database backend
 *
 * @param _username
 * @param _password
 * @throws Exception
 */
static void add_user(String _username, String _password) throws Exception {
    EUser u = new EUser();
    u.password = FHashGenerator.generate(_password);
    u.username = _username;
    u.externalUser = false;
    EntityManager em = Database.get_instance()
                               .getEm();
    em.getTransaction()
      .begin();
    ;
    em.persist(u);
    em.getTransaction()
      .commit();
    em.close();
}

/**
 * Interface got hooked up on specific functions on login
 */
public interface UserLoginEvent {
    void logged_in(PostNewSessionResponse ps_response, PostLoginResponse pl_response,
                   Header header,AsymmetricKeyPair client_kp) throws Exception;
}
}
