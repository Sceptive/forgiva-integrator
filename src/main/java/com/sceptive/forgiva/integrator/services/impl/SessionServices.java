package com.sceptive.forgiva.integrator.services.impl;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.AsymmetricKeyPair;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.FHashGenerator;
import com.sceptive.forgiva.integrator.core.db.objects.ELoginLog;
import com.sceptive.forgiva.integrator.core.db.objects.ESession;
import com.sceptive.forgiva.integrator.core.db.objects.EUser;
import com.sceptive.forgiva.integrator.core.db.objects.EUserGroup;
import com.sceptive.forgiva.integrator.core.ldap.LdapConnection;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.SecurityPermission;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

/*
    Implement web service functions related with session initializations and authentications such
     as login/logout etc.
 */
public class SessionServices {



public static Response newsession(PostNewSessionRequest _postNewSessionRequest)
        throws NotFoundException {
    SecurityPermission permission =
            SecurityManager.validate_session(_postNewSessionRequest.getHeader());
    if (_postNewSessionRequest.getClientPk() == null ||
        !Common.check_if_hex(_postNewSessionRequest.getClientPk())) {
        return Response.status(Response.Status.UNAUTHORIZED)
                       .build();
    }
    PostNewSessionResponse pns_response = new PostNewSessionResponse();
    // Session is invalid or should be initialized
    if (permission.session == null) {
        permission.session =
                SecurityManager.create_unauthenticated_session(_postNewSessionRequest.getClientPk());
        pns_response.setNewSessionId(permission.session.id);
    } else {
        pns_response.logonState(new LogonState().authenticated(permission.session.authenticated)
                                                .isAdmin(permission.session.administrator_session));
        EntityManager em = Database.get_instance()
                                   .getEm();
        em.getTransaction()
          .begin();
        permission.session.client_public_key = _postNewSessionRequest.getClientPk();
        em.merge(permission.session);
        em.getTransaction()
          .commit();
        em.close();
    }
    pns_response.setHshSalt(Configuration.runtime.security_default_hashing_salt);
    pns_response.setHshAlg(Configuration.runtime.security_default_hashing);
    pns_response.setSessionPk(permission.session.public_key);
    pns_response.setLdapEnabled(Configuration.runtime.ldap_server != null && Configuration.runtime.ldap_server.trim()
            .length() >
            1);
    return Response.ok()
                   .entity(pns_response)
                   .build();
}

public static LdapConnection login_for_ldap_user(String _userName, String _password) {
    for (String server_url : Configuration.runtime.ldap_server.split(",")) {
        LdapConnection connection = new LdapConnection(server_url,
                                                       Configuration.runtime.ldap_domain,
                                                       _userName,
                                                       _password);
        if (connection.isAuthorized()) {
            connection.getConnection().setTimeOut(99999999);
            return connection;
        }

        connection.close();
    }
    return null;
}

private static boolean invoke_ldap_login(String _userName, String _password, ESession _session)
        throws InvalidValueException, NotInitializedException {
    LdapConnection connection = login_for_ldap_user(_userName,
                                                    _password);
    boolean ok = false;
    if (connection != null && connection.isAuthorized()) { // authentication succeeded
        // Checking for default LDAP group
        EntityManager em = Database.get_instance()
                                   .getEm();
        Query sub_groups_query = em.createQuery(
                "SELECT u FROM EUserGroup u WHERE u.parentUserGroupId = 0 " +
                " AND u.userGroupName = :ugn",
                EUserGroup.class)
                                   .setParameter("ugn",
                                                 Constants.DEFAULT_LDAP_USERGROUP_NAME);
        EUserGroup       ldap_users_group = null;
        List<EUserGroup> sub_groups       = sub_groups_query.getResultList();
        if (sub_groups.size() > 0) {
            ldap_users_group = sub_groups.get(0);
        } else {
            try {
                ldap_users_group =
                        AdminServices.add_usergroup_ex(Constants.DEFAULT_LDAP_USERGROUP_NAME,
                                                       Constants.DEFAULT_LDAP_USERGROUP_DESCRIPTION,
                                                       "0");
            }
            catch (Exception _ex) {
                Warning.get_instance()
                       .print(_ex);
            }
        }
        if (ldap_users_group == null) {
            Warning.get_instance()
                   .print("Could not create %s",
                          Constants.DEFAULT_LDAP_USERGROUP_NAME);
            return false;
        }
        List res = em.createQuery("SELECT u FROM EUser u WHERE " +
                                  " u.username = :username AND u.externalUser = true")
                     .setParameter("username",
                                   _userName)
                     .getResultList();
        if (res.size() == 0) {
            EUser new_user = new EUser();
            new_user.fullname       = connection.getUserInfo().userFullName;
            new_user.username       = _userName;
            new_user.email          = connection.getUserInfo().userEmail;
            new_user.externalUser   = true;
            // In cases of if user does not want to use master key everytime for password generation
            // it is necessary to use password hash as master key. That's why LDAP password hash is saved.
            new_user.password       = Common.encodeHex(FHashGenerator.hash_for_model(
                    Configuration.runtime.security_pw_hashing_model,
                    _password.getBytes(),
                    Common.decodeHex(Configuration.runtime.security_pw_hashing_salt)));

            // If there is no groupId defined set it as default user group
            new_user.groupId        = ldap_users_group.id;
            em.getTransaction()
              .begin();
            em.persist(new_user);
            em.getTransaction()
              .commit();
            // Create default metadata group for user
            CommonOperations.create_default_metadatagroup_for_user(new_user.id);
            _session.user_id = new_user.id;
            Info.get_instance()
                .print("New External User: %s - %s for Group %s",
                       new_user.username,
                       new_user.id,
                       new_user.groupId);
        } else {
            EUser user = (EUser) res.get(0);
            _session.user_id               = user.id;
        }

        em.getTransaction()
          .begin();
        _session.authenticated         = true;
        _session.auth_ts               = LocalDateTime.now();
        _session.administrator_session = false;

        em.merge(_session);
        em.getTransaction()
          .commit();
        ok = true;
    } else { // authentication failed
        //                                    String            msg  = response
        //                                    .getDiagnosticMessage(); // read the failure message
        //                                    ResponseControl[] ctls = response.getControls(); //
        //                                    read any response controls
        Warning.get_instance()
               .print("Invalid login for %s",
                      _userName);
    }
    if (connection != null) {
        connection.close();
    }
    return ok;
}

// Stores username and epoch seconds for start of jail time if any ban occurs
private static HashMap<String,Long> jail_list = new HashMap<>();

private static void store_login_attempt(String _username, boolean _result) {
    EntityManager em = Database.get_instance()
                               .getEm();


    //store succesfull login
    em.getTransaction().begin();

    ELoginLog loginLog=new ELoginLog();
    loginLog.userName   = _username;
    loginLog.success    = _result;
    loginLog.tryDate    = LocalDateTime.now();

    em.merge(loginLog);
    em.getTransaction().commit();
    em.close();
}

public static Response login(PostLoginRequest _postLoginRequest) throws NotFoundException {
    // Get security permissions
    SecurityPermission sp = SecurityManager.validate_session(_postLoginRequest.getHeader());
    // If 2fa required
    boolean TwoFactorAuth_required = false;
    // If we have a valid session and not authenticated
    if (sp.session != null) {
        // If session is already authenticated
        if (sp.session.authenticated != null && sp.session.authenticated) {
            sp.response = Response.status(Response.Status.OK)
                                  .build();
        } else {
            // Authenticate
            // Set response as not authenticated by default
            sp.response = Response.ok(new PostLoginResponse().twoFARequired(false)
                                                             .logonState(new LogonState().authenticated(false)
                                                                                         .isAdmin(false)))
                                  .build();
            // Check if data seems valid
            if (_postLoginRequest.getUsername() != null &&
                _postLoginRequest.getPassword() != null &&
                Common.check_if_hex(_postLoginRequest.getUsername()) &&
                Common.check_if_hex(_postLoginRequest.getPassword())) {


                EntityManager em = Database.get_instance()
                                           .getEm();

                try {
                    // Initializing Asymmetric Cryptography keypairs
                    AsymmetricKeyPair asckp =
                            Asymmetric.init_cipherkeypair(sp.session.client_public_key,
                                                          sp.session.private_key);
                    // Decrypted username
                    String decrypted_username = new String(Asymmetric.decrypt(asckp,
                                                                              Common.decodeHex(_postLoginRequest.getUsername())));
                    // Hashed password in hex ran through SECURITY_DEFAULT_HASHING algorithm
                    byte[] decrypted_password_hash = Asymmetric.decrypt(asckp,
                                                                        Common.decodeHex(_postLoginRequest.getPassword()));
                    LocalDateTime ldNow = LocalDateTime.now();

                    if (jail_list.containsKey(decrypted_username)) {

                        Long          jail_start_time       = jail_list.get(decrypted_username);
                        LocalDateTime jail_start_time_ld    = LocalDateTime.ofEpochSecond(jail_start_time, 0, ZoneOffset.UTC);


                        Duration duration = Duration.between(jail_start_time_ld,ldNow);



                        long minutes_since = duration.toMinutes();

                        if (minutes_since <= Configuration.runtime.login_ban_time) {
                            throw new NotFoundException(0, String.format("User %s banned for %d minutes on %d.",
                                                                         decrypted_username,
                                                                         Configuration.runtime.login_ban_time, minutes_since));
                        } else {

                            // Take the account out of jail
                            jail_list.remove(decrypted_username);
                        }

                    }



                    LocalDateTime banCheckTime = ldNow.minus(Configuration.runtime.login_ban_time, ChronoUnit.MINUTES);


                    // find login trys.
                    Long loginCount = (Long) em.createQuery("SELECT COUNT(u) FROM ELoginLog u WHERE " +
                                                    " u.userName = :username AND u.success = false And u.tryDate > :trydate")
                                          .setParameter("username",decrypted_username)
                                          .setParameter("trydate",banCheckTime)
                                               .getSingleResult();


                    //check failed login.
                    if(loginCount >= Configuration.runtime.login_max_retry) {

                        jail_list.put(decrypted_username, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
                        throw new NotFoundException(0, String.format("Login try count exceeded for user %s",decrypted_username));
                    }


                    if (_postLoginRequest.getLoginOverLdap() != null &&
                        _postLoginRequest.getLoginOverLdap() && Configuration.runtime != null &&
                        Configuration.runtime.ldap_server != null &&
                        Configuration.runtime.ldap_server.trim()
                                                         .length() > 1) {
                        // Go for LDAP login and set ok if succeded
                        if (invoke_ldap_login(decrypted_username,
                                              new String(decrypted_password_hash),
                                              sp.session)) {
                            sp.response = Response.ok(new PostLoginResponse().twoFARequired(false)
                                                                             .logonState(new LogonState()
                                                                                                 .authenticated(true)
                                                                                                 .isAdmin(false)))
                                                  .build();
                        }
                    } else {
                        // FHash value ran with chosen SECURITY_PW_HASHING_MODEL combining
                        // SECURITY_PW_HASHING_SALT value
                        assert Configuration.runtime != null;
                        String password =
                                Common.encodeHex(FHashGenerator.hash_for_model(Configuration.runtime.security_pw_hashing_model,
                                                                               decrypted_password_hash,
                                                                               Common.decodeHex(Configuration.runtime.security_pw_hashing_salt)));
                        // Checking whether username and password does exists

                        List res = em.createQuery("SELECT u FROM EUser u WHERE " +
                                                  " u.username = :username AND u.password = " +
                                                  ":password and u.externalUser = false")
                                     .setParameter("username",
                                                   decrypted_username)
                                     .setParameter("password",
                                                   password)
                                     .getResultList();

                        // If there is no user
                        if (res.size() == 0) {
                            // Check if it's administrator login
                            if (Common.safe_compare(Configuration.runtime.administrator_username,
                                                    decrypted_username) &&
                                Common.safe_compare(Configuration.runtime.administrator_password,
                                                    password)) {
                                em.getTransaction()
                                  .begin();
                                sp.session.authenticated         = true;
                                sp.session.auth_ts               = LocalDateTime.now();
                                sp.session.administrator_session = true;
                                em.merge(sp.session);
                                em.getTransaction()
                                  .commit();
                                // Set response as authenticated as admin
                                sp.response =
                                        Response.ok(new PostLoginResponse().twoFARequired(TwoFactorAuth_required)
                                                                           .logonState(new LogonState().authenticated(true)
                                                                                                       .isAdmin(true)))
                                                .build();
                                Info.get_instance()
                                    .print("Administrator login from session id: %s.",
                                           sp.session.id);
                                // Store successful login
                                store_login_attempt(decrypted_username,true);
                            } else {

                                //store failed login attempt
                                store_login_attempt(decrypted_username,false);

                            }
                        }
                        // If there is user with specified password
                        else {
                            EUser user = ((EUser) res.get(0));
                            em.getTransaction()
                              .begin();
                            sp.session.authenticated         = true;
                            sp.session.auth_ts               = LocalDateTime.now();
                            sp.session.administrator_session = false;
                            sp.session.user_id               = user.id;
                            em.merge(sp.session);
                            em.getTransaction()
                              .commit();
                            // Set response as authenticated as normal user
                            sp.response =
                                    Response.ok(new PostLoginResponse().twoFARequired(TwoFactorAuth_required)
                                                                       .logonState(new LogonState().authenticated(true)
                                                                                                   .isAdmin(false)))
                                            .build();
                            Info.get_instance()
                                .print("%s logged in session id: %s",
                                       user.username,
                                       sp.session.id);
                            // Store successful login
                            store_login_attempt(decrypted_username,true);
                        }
                        if (sp.session != null && sp.session.authenticated != null &&
                            !sp.session.authenticated.booleanValue() && TwoFactorAuth_required) {
                            //TODO: Implement 2FA model
                        }
                    }
                }
                catch (Exception e) {
                    Warning.get_instance()
                           .print("Catched exception: %s",
                                  e.getMessage());
                    Warning.get_instance()
                           .print(e);
                }
                finally {
                   	em.close();
                }
            }
        }
    }
    return sp.response;
}

public static Response logout(PostLogoutRequest _postLogoutRequest) {
    // Get security permissions
    SecurityPermission sp = SecurityManager.validate_session(_postLogoutRequest.getHeader());
    // If there is a session then invalidate it
    if (sp.session != null) {
        SecurityManager.invalidate_session(sp.session);
    }
    return Response.ok()
                   .build();
}

public static Response login_2fa(PostLogin2faRequest _postLogin2faRequest)
        throws NotFoundException {
    throw new NotFoundException(404,
                                "Not Implemented");
}
}
