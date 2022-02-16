package com.sceptive.forgiva.integrator.services.impl;

import com.sceptive.forgiva.integrator.core.*;
import com.sceptive.forgiva.integrator.core.crypto.*;
import com.sceptive.forgiva.integrator.core.db.objects.*;
import com.sceptive.forgiva.integrator.core.fsconnectors.IFSResponse;
import com.sceptive.forgiva.integrator.core.ldap.LdapConnection;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class UserServices {

/*

 */
public static Response generate_password(PostUserGenerateRequest _postUserGenerateRequest)
        throws NotFoundException {
    return SecurityManager
            .secure_invoke(_postUserGenerateRequest.getHeader(), false, (session) -> {

                PostUserGenerateResponse ret = new PostUserGenerateResponse();

                try {

                    EntityManager em = Database.get_instance().getEm();


                    Long mId  = Long.parseLong(_postUserGenerateRequest.getMetadataId());
                    EMetadata metadata = em.createQuery(
                            "SELECT m FROM EMetadata m WHERE " + " m.userId = :userId " +
                            " AND m.id = :mId", EMetadata.class)
                                            .setParameter("userId", session.user_id)
                                            .setParameter("mId", mId)
                                            .getSingleResult();

                    byte[] decrypted_masterkey_hash = Asymmetric
                            .decrypt_using_session(Common.decodeHex(_postUserGenerateRequest.getMasterKey()), session);

                    ret.setResult(new OperationResult().info("ok"));

                    boolean[]       executed = new boolean[] { false };
                    boolean[]       has_error = new boolean[] { false };
                    final String[]  generated = new String[] { ""};

                    if (metadata.lastRenewal == null) {
                        metadata.lastRenewal = "";
                    }
                    ForgivaServerInvoker.get_instance().execute(metadata,
                                                                _postUserGenerateRequest.getVisualConfirmation(),
                                                                Common.encodeHex(decrypted_masterkey_hash),
                                                                "",
                                                                new IFSResponse() {
                                                                    @Override
                                                                    public void ok(
                                                                            final String _favorite_animal,
                                                                            final String _password) {

                                                                        executed[0] = true;
                                                                        generated[0] = _password;
                                                                    }

                                                                    @Override
                                                                    public void failed(
                                                                            final String _error_code) {

                                                                        executed[0] = true;
                                                                        has_error[0] = true;
                                                                    }
                                                                });

                    while (!executed[0]) {
                        Thread.sleep(10);
                    }

                    if (has_error[0]) {
                        throw new Exception("Password generation failed");
                    }

//                    Info.get_instance().print("Generated: %s",generated[0]);
//                    byte[] generated_password = Hash2Password.gen_password(metadata,decrypted_masterkey_hash).getBytes("UTF-8");

                    byte[] encrypted_password = Asymmetric.encrypt_using_session( Common.decodeHex(generated[0]),session);

                    ret.setGeneratedPassword(Common.encodeHex(encrypted_password));


                } catch (Exception ex) {
                    ret.setResult(new OperationResult().error("Invalid data"));
                }

                return Response.ok().entity(ret).build();
            });
}

/**
 * Adds metadata group defined in request to the database
 *
 * @param _postUserMetadataGroupAddRequest
 * @return
 */
public static Response add_metadatagroup(
        PostUserMetadataGroupAddRequest _postUserMetadataGroupAddRequest) {

    return SecurityManager
            .secure_invoke(_postUserMetadataGroupAddRequest.getHeader(), false, (session) -> {
                OperationResult ret = new OperationResult();

                try {
                    EMetadataGroup metadataGroup = new EMetadataGroup();
                    metadataGroup.userId = session.user_id;
                    if (_postUserMetadataGroupAddRequest.getGroup() == null ||
                        _postUserMetadataGroupAddRequest.getGroup().getGroupName() == null ||
                        _postUserMetadataGroupAddRequest.getGroup().getGroupName().isEmpty() ||
                        _postUserMetadataGroupAddRequest.getGroup().getGroupName().trim().equalsIgnoreCase("")
                        ) {
                        throw new InvalidValueException("Empty group");
                    }
                    metadataGroup.groupName        =
                            _postUserMetadataGroupAddRequest.getGroup().getGroupName();
                    metadataGroup.groupDescription =
                            _postUserMetadataGroupAddRequest.getGroup().getGroupDescription();
                    if (_postUserMetadataGroupAddRequest.getGroup().getParentGroupId() != null) {
                        metadataGroup.parentGroupId = Long.valueOf(
                                _postUserMetadataGroupAddRequest.getGroup().getParentGroupId());
                    }

                    EntityManager em = Database.get_instance().getEm();
                    em.getTransaction().begin();
                    em.persist(metadataGroup);
                    em.getTransaction().commit();
                    em.close();
                    ret.addAffectedRecordsItem(String.valueOf(metadataGroup.id));


                }
                catch (Exception ex) {
                   // Info.get_instance().print(ex);
                    ret.error("Invalid data");
                }

                return Response.ok().entity(ret).build();
            });

    // return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
}

/**
 * Adds new metadata to user metadata table
 *
 * @param _postUserMetadataAddRequest
 * @return
 */
public static Response add_metadata(PostUserMetadataAddRequest _postUserMetadataAddRequest) {

    return SecurityManager
            .secure_invoke(_postUserMetadataAddRequest.getHeader(), false, (session) -> {
                OperationResult ret = new OperationResult();

                try {

                    EMetadata metadata = new EMetadata();

                    metadata.userId = session.user_id;

                    if (_postUserMetadataAddRequest.getPasswordLength() == null ||
                        _postUserMetadataAddRequest.getMetadata() == null ||
                        _postUserMetadataAddRequest.getMetadata().getAccount() == null ||
                        _postUserMetadataAddRequest.getMetadata().getHost() == null ||
                        _postUserMetadataAddRequest.getMetadata().getComplexity() == null ||
                        _postUserMetadataAddRequest.getOptLowercase() == null ||
                        _postUserMetadataAddRequest.getOptUppercase() == null ||
                        _postUserMetadataAddRequest.getOptSymbols() == null ||
                        _postUserMetadataAddRequest.getOptNumbers() == null ||
                        // If all password requirements are false then fail
                        (!_postUserMetadataAddRequest.getOptLowercase() &&
                          !_postUserMetadataAddRequest.getOptUppercase() &&
                          !_postUserMetadataAddRequest.getOptSymbols() &&
                          !_postUserMetadataAddRequest.getOptNumbers()
                        ) ||
                        // If complexity is not between 1-3 inclusiv
                        (_postUserMetadataAddRequest.getMetadata().getComplexity() < 1 ||
                         _postUserMetadataAddRequest.getMetadata().getComplexity() > 3
                        ) ||
                        // If both account and host are empty
                        (_postUserMetadataAddRequest.getMetadata().getAccount().trim().isEmpty() &&
                         _postUserMetadataAddRequest.getMetadata().getHost().trim().isEmpty()
                        ) ||
                        // If password length is invalid (No we wont generate single digit password)
                        (_postUserMetadataAddRequest.getPasswordLength() <= 1 ||
                         _postUserMetadataAddRequest.getPasswordLength() > 32)
                    ) {

                        throw new InvalidValueException("Invalid metadata");
                    }



                    metadata.passwordLength  = _postUserMetadataAddRequest.getPasswordLength();
                    metadata.account         =
                            _postUserMetadataAddRequest.getMetadata().getAccount();
                    metadata.host            = _postUserMetadataAddRequest.getMetadata().getHost();
                    metadata.complexity      =
                            _postUserMetadataAddRequest.getMetadata().getComplexity();
                    metadata.metadataGroupId =
                            Long.parseLong(_postUserMetadataAddRequest.getMetadata().getGroupId());
                    metadata.generatedBefore = false;
                    metadata.lastRenewal     = null;
                    metadata.optLowercase    = _postUserMetadataAddRequest.getOptLowercase();
                    metadata.optNumbers      = _postUserMetadataAddRequest.getOptNumbers();
                    metadata.optSymbols      = _postUserMetadataAddRequest.getOptSymbols();
                    metadata.optUppercase    = _postUserMetadataAddRequest.getOptUppercase();

                    EntityManager em = Database.get_instance().getEm();
                    em.getTransaction().begin();
                    em.persist(metadata);
                    em.getTransaction().commit();
                    em.close();
                    ret.addAffectedRecordsItem(String.valueOf(metadata.id));

                }
                catch (Exception ex) {
                    //  Info.get_instance().print(ex);
                    ret.error("Invalid metadata");
                }

                return Response.ok().entity(ret).build();
            });
}

/**
 * Returns metadatas in group starts with startIdx, with count within metadata group specified
 * with metadataGroupId
 *
 * @param _request
 * @return
 */
public static Response get_metadata_bygroupId(PostUserMetadataBygroupRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), false, (session) -> {
        PostUserMetadataBygroupResponse response = new PostUserMetadataBygroupResponse();

        try {
            Long group_id  = Long.parseLong(_request.getGroupId());
            int  start_idx = _request.getStartIdx();
            int  count     = _request.getCount();

            if (start_idx < 0 || count < 0) {
                throw new InvalidValueException("Invalid start index or count value");
            }

            EntityManager em = Database.get_instance().getEm();

            // TODO this is lame to count total records everytime. There should be
            //  another way to implement this.
            Long total_count = (Long) em.createQuery(
                    "SELECT COUNT(m) FROM EMetadata m WHERE m.userId = :userId " +
                    " AND  m.metadataGroupId = :metadataGroupId ")
                                        .setParameter("userId", session.user_id)
                                        .setParameter("metadataGroupId", group_id)
                                        .getSingleResult();

            List<EMetadata> res = em.createQuery(
                    "SELECT m FROM EMetadata m WHERE " + " m.userId = :userId AND m" +
                    ".metadataGroupId = :metadataGroupId " + "ORDER BY m.id", EMetadata.class)
                                    .setParameter("userId", session.user_id)
                                    .setParameter("metadataGroupId", group_id)
                                    .setFirstResult(start_idx)
                                    .setMaxResults(count).getResultList();

            for (EMetadata e_metadata : res) {
                response.addMetadatasItem(
                        new Metadata().account(e_metadata.account).host(e_metadata.host)
                                      .complexity(e_metadata.complexity)
                                      .metadataId(String.valueOf(e_metadata.id))
                                      .generatedBefore(e_metadata.generatedBefore)
                                      .groupId(String.valueOf(e_metadata.metadataGroupId))
                                      .lastRenewal(e_metadata.lastRenewal));
            }

            response.setStartIdx(_request.getStartIdx());
            response.setTotalRecords(total_count.intValue());
        }
        catch (Exception e) {
            Warning.get_instance().print(e);
        }

        return Response.ok().entity(response).build();
    });
}

private static int remove_metadatagroup_internal(Long _metadataGroupId, ESession _session) {

    int ret = 0;

    EntityManager em = Database.get_instance().getEm();
    Query sub_groups_query = em.createQuery("SELECT m FROM EMetadataGroup m WHERE " +
                                            " m.parentGroupId = :mgId AND m.userId = :userId",
                                            EMetadataGroup.class)
                               .setParameter("mgId", _metadataGroupId)
                               .setParameter("userId", _session.user_id);
    List<EMetadataGroup> sub_groups = sub_groups_query.getResultList();

    for (EMetadataGroup sub_group : sub_groups) {
        ret += remove_metadatagroup_internal(sub_group.id, _session);
    }

    Query query = em.createQuery(
            "DELETE FROM EMetadataGroup m WHERE m.id = :mId " + " AND m.userId = :userId")
                    .setParameter("mId", _metadataGroupId).setParameter("userId", _session.user_id);
    em.getTransaction().begin();
    int rowCount = query.executeUpdate();
    em.getTransaction().commit();
    // If any metadatagroup is deleted then remove the metadatas related to it
    if (rowCount > 0) {

        query = em.createQuery("DELETE FROM EMetadata m WHERE m.metadataGroupId = :mgId " +
                               " AND m.userId = :userId").setParameter("mgId", _metadataGroupId)
                  .setParameter("userId", _session.user_id);
        em.getTransaction().begin();
        rowCount = query.executeUpdate();
        em.getTransaction().commit();

        ret += rowCount;
        //            info_resp = String.format("%d metadata removed",rowCount);

    }
    em.close();
    return ret;
}

/**
 * @param _postUserMetadataGroupRemoveRequest
 * @return
 * @throws NotFoundException
 */
public static Response remove_metadatagroup(
        PostUserMetadataGroupRemoveRequest _postUserMetadataGroupRemoveRequest)
        throws NotFoundException {

    return SecurityManager
            .secure_invoke(_postUserMetadataGroupRemoveRequest.getHeader(), false, (session) -> {
                String info_resp  = new String();
                String error_resp = null;

                if (_postUserMetadataGroupRemoveRequest.getMetadataGroupId() != null) {

                    try {

                        Long metadataGroupId = Long.parseLong(
                                _postUserMetadataGroupRemoveRequest.getMetadataGroupId());

                        int count = remove_metadatagroup_internal(metadataGroupId, session);
                        info_resp = String.format("%d metadata removed.", count);

                        // In case user removes all metadata groups then we should create default
                        // one
                        CommonOperations.create_default_metadatagroup_for_user(session.user_id);

                    }
                    catch (Exception e) {

                        error_resp = "Invalid data";
                        Warning.get_instance().print(e);

                        // TODO There should be incident log integration
                    }
                }

                return Response.ok().entity(new OperationResult().error(error_resp).info(info_resp))
                               .build();
            });
}

/**
 * @param _postUserMetadataGroupsRequest
 * @return
 * @throws NotFoundException
 */
public static Response get_metadata_groups(
        PostUserMetadataGroupsRequest _postUserMetadataGroupsRequest) throws NotFoundException {
    return SecurityManager
            .secure_invoke(_postUserMetadataGroupsRequest.getHeader(), false, (session) -> {
                PostUserMetadataGroupsResponse response = new PostUserMetadataGroupsResponse();

                try {
                    EntityManager em = Database.get_instance().getEm();
                    Query query = em.createQuery(
                            "SELECT m FROM EMetadataGroup m WHERE m.userId = :userId AND m.parentGroupId = 0",
                            EMetadataGroup.class).setParameter("userId", session.user_id);
                    List<EMetadataGroup> res = query.getResultList();

                    for (EMetadataGroup m : res) {

                        response.addGroupsItem(new MetadataGroup().groupId(String.valueOf(m.id))
                                                                  .groupDescription(
                                                                          m.groupDescription)
                                                                  .groupName(m.groupName)
                                                                  .parentGroupId(String.valueOf(
                                                                          m.parentGroupId)));
                        Query _subGroupsQuery =
                                em.createQuery("SELECT m FROM EMetadataGroup m WHERE m.userId = :userId AND m.parentGroupId = :pId",
                                               EMetadataGroup.class)
                                  .setParameter("userId", session.user_id)
                                  .setParameter("pId",m.id);
                        List<EMetadataGroup> res_sg = _subGroupsQuery.getResultList();
                        for (EMetadataGroup m_sub : res_sg) {
                            response.addGroupsItem(new MetadataGroup().groupId(String.valueOf(m_sub.id))
                                                                      .groupDescription(
                                                                              m_sub.groupDescription)
                                                                      .groupName(m_sub.groupName)
                                                                      .parentGroupId(String.valueOf(
                                                                              m_sub.parentGroupId)));
                        }

                    }
                }
                catch (Exception e) {
                    Info.get_instance().print(e);
                }

                return Response.ok().entity(response).build();
            });
}

/**
 * @param _postUserMetadataHostRequest
 * @return
 * @throws NotFoundException
 */
public static Response get_metadata_hosts(PostUserMetadataHostRequest _postUserMetadataHostRequest)
        throws NotFoundException {
    return SecurityManager
            .secure_invoke(_postUserMetadataHostRequest.getHeader(), false, (session) -> {
                PostUserMetadataHostResponse response = new PostUserMetadataHostResponse();

                try {

                    if (_postUserMetadataHostRequest.getFilter() == null) {
                        throw new InvalidValueException("Filter cannot be null");
                    }
                    EntityManager em = Database.get_instance().getEm();
                    Query query = em.createQuery(
                            "SELECT DISTINCT m.host FROM EMetadata m WHERE m.userId = :userId " +
                            " AND m.host LIKE :host", EMetadata.class)
                                    .setParameter("userId", session.user_id).setParameter("host",
                                                                                          "%" +
                                                                                          _postUserMetadataHostRequest
                                                                                                  .getFilter() +
                                                                                          "%");
                    List<String> res = query.getResultList();

                    for (String h : res) {
                        response.addHostsItem(new PostUserMetadataHostResponseHosts().host(h));
                    }
                }
                catch (Exception e) {
                    Info.get_instance().print(e);
                }

                return Response.ok().entity(response).build();
            });
}

/**
 * Removes metadata specified with metadataId
 *
 * @param _postUserMetadataRemoveRequest
 * @return
 * @throws NotFoundException
 */
public static Response remove_metadata(PostUserMetadataRemoveRequest _postUserMetadataRemoveRequest)
        throws NotFoundException {
    return SecurityManager
            .secure_invoke(_postUserMetadataRemoveRequest.getHeader(), false, (session) -> {
                OperationResult ret = new OperationResult();

                try {

                    if (_postUserMetadataRemoveRequest.getMetadataId() != null) {

                        long m_id = Long.parseLong(_postUserMetadataRemoveRequest.getMetadataId());

                        EntityManager em = Database.get_instance().getEm();
                        Query query = em.createQuery(
                                "SELECT m FROM EMetadata m WHERE m.id = :mId " +
                                " AND m.userId = :userId", EMetadata.class)
                                        .setParameter("mId", m_id)
                                        .setParameter("userId", session.user_id);
                        List res = query.getResultList();

                        if (res.size() == 1) {
                            em.getTransaction().begin();
                            em.remove(res.get(0));
                            em.getTransaction().commit();
                            em.close();
                            ret.addAffectedRecordsItem(
                                    _postUserMetadataRemoveRequest.getMetadataId());
                        } else {
                            ret.error("Metadata does not exists.");
                        }
                    }

                }
                catch (Exception e) {
                    Info.get_instance().print(e);
                    ret.info("Invalid data");
                }

                return Response.ok().entity(ret).build();
            });
}

/**
 * Returns list of metadata filtered by likeliness of criteria within host and account fields
 *
 * @param _postUserMetadataSearchRequest
 * @return
 * @throws NotFoundException
 */
public static Response search_metadata(PostUserMetadataSearchRequest _postUserMetadataSearchRequest)
        throws NotFoundException {

    return SecurityManager
            .secure_invoke(_postUserMetadataSearchRequest.getHeader(), false, (session) -> {
                PostUserMetadataSearchResponse response = new PostUserMetadataSearchResponse();

                try {

                    String criteria = _postUserMetadataSearchRequest.getCriteria();

                    EntityManager em = Database.get_instance().getEm();
                    Query query = em.createQuery(
                            "SELECT m FROM EMetadata m WHERE m.userId = :userId " +
                            " AND (m.host LIKE :criteria OR m.account LIKE :criteria)",
                            EMetadata.class).setParameter("userId", session.user_id)
                                    .setParameter("criteria", "%" + criteria + "%");
                    List<EMetadata> res = query.getResultList();

                    for (EMetadata m : res) {
                        response.addMetadataItem(new Metadata().metadataId(String.valueOf(m.id))
                                                               .groupId(String.valueOf(
                                                                       m.metadataGroupId))
                                                               .host(m.host).account(m.account)
                                                               .lastRenewal(m.lastRenewal)
                                                               .complexity(m.complexity)
                                                               .generatedBefore(m.generatedBefore));
                    }
                }
                catch (Exception e) {
                    Info.get_instance().print(e);
                }

                return Response.ok().entity(response).build();
            });
}

/**
 *
 * Sets user's settings regarding key-value couple of setting preference
 *
 * @param _request
 * @return
 */
public static Response settings_set(PostUserSettingsSetRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), false, (session) -> {

        OperationResult response = new OperationResult();

        try {

            final String key    = _request.getKey();

            if (key == null ||
                _request.getValue() == null ||
                !Constants.USER_SETTINGS_KEYS.contains(key) ||
                _request.getValue().trim().length() == 0
                ) {
                throw new InvalidValueException("Request contains invalid data");
            }


            final int key_index = Constants.USER_SETTINGS_KEYS.indexOf(key);
            String clsName      = Constants.USER_SETTINGS_TYPES.get(key_index).getName();

            String save_value = _request.getValue();

            // Validating values. Important for security.
            if (clsName.equalsIgnoreCase(Boolean.class.getName())) {
               save_value = Boolean.toString(Boolean.parseBoolean(save_value));
            } else if (clsName.equalsIgnoreCase(Integer.class.getName())) {
                save_value = Integer.toString(Integer.parseInt(save_value));
            }

            EntityManager em = Database.get_instance().getEm();
            EUserSetting setting = em.createQuery(
                    "SELECT m FROM EUserSetting m WHERE " + " m.userId = :userId " +
                    " AND m.key = :key", EUserSetting.class)
                                   .setParameter("userId", session.user_id)
                                   .setParameter("key", key)
                                   .getSingleResult();
            if (setting == null) {
                setting         = new EUserSetting();
                setting.userId  = session.user_id;
                setting.key     = key;
                setting.value   = save_value;

                em.getTransaction().begin();
                em.persist(setting);
                em.getTransaction().commit();


            } else {
                setting.value   = save_value;
                em.getTransaction().begin();
                em.merge(setting);
                em.getTransaction().commit();
            }
            em.close();

            response.setInfo("Ok");

        } catch (Exception e) {
            Warning.get_instance().print(e);
            response.error("Could not set setting");
        }

        return Response.ok().entity(response).build();
    });


}

/**
 *
 * Returns user's settings queried with key in the request
 *
 * @param _request
 * @return
 */
public static Response settings_get(PostUserSettingsGetRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), false, (session) -> {

        PostUserSettingsGetResponse response = new PostUserSettingsGetResponse();

        try {
            final String key    = _request.getKey();

            if (key == null ||
                !Constants.USER_SETTINGS_KEYS.contains(key)
            ) {
                throw new InvalidValueException("Request contains invalid data");
            }

            final int key_index = Constants.USER_SETTINGS_KEYS.indexOf(key);


            EntityManager em = Database.get_instance().getEm();
            EUserSetting setting = em.createQuery(
                    "SELECT m FROM EUserSetting m WHERE " + " m.userId = :userId " +
                    " AND m.key = :key", EUserSetting.class)
                                     .setParameter("userId", session.user_id)
                                     .setParameter("key", key)
                                     .getSingleResult();
            if (setting == null) {
               response.setValue(Constants.USER_SETTINGS_DEFAULT_VALUES.get(key_index));
            } else {
                response.setValue(setting.value);
            }


        } catch (Exception e) {
            Warning.get_instance().print(e);
            response.setValue(null);
        }


        return Response.ok().entity(response).build();
    });


}

/**
 *
 * Changes user's password and invalidates open sessions
 *
 *
 * @param _request
 * @return
 */
public static Response change_password(PostUserPasswordChangeRequest _request) {


    return SecurityManager.secure_invoke(_request.getHeader(), false, (session) -> {

        OperationResult response = new OperationResult();

        try {


            if (PasswordGuard.get_instance().isGenerating_hashset()) {
                response.setError("Password Guard is launching. Please try again later.");
                return Response.ok().entity(response).build();
            }

            // Get user object

            EntityManager em = Database.get_instance()
                                       .getEm();

            EUser user = em.createQuery("SELECT u FROM EUser u WHERE " +
                                             " u.id = :userId",EUser.class)
                                .setParameter("userId",
                                       session.user_id)
                                .getSingleResult();

            // Should not happen
            if (user == null) {
                throw new InvalidValueException("No user found with user id stored in session");
            }

            byte[] decrypted_old_password = Asymmetric.decrypt_using_session(Common.decodeHex(_request.getOldPassword()),session);

            byte[] decrypted_new_password = Asymmetric.decrypt_using_session(Common.decodeHex(_request.getNewPassword()),session);

            String plain_text_new_password  = new String(decrypted_new_password,
                                                         StandardCharsets.UTF_8);

            String plain_text_old_password = new String(decrypted_old_password,
                                                        StandardCharsets.UTF_8);

            PasswordGuard.get_instance().validate_password(plain_text_new_password,
                                                         new PasswordGuard.PasswordValidationResponse() {
                                                             @Override
                                                             public void ok() {
                                                             }

                                                             @Override
                                                             public void failed(
                                                                     final String _message) {
                                                                 response.setError(_message);
                                                             }
                                                         });

            Info.get_instance().print("Error: %s | %s",response.getError(),plain_text_new_password);

            if (response.getError() == null) {

                // It is ok for password guard
                if (user.externalUser) {

                    // Check if external user
                    // In case of external user current password does get send in plain text encrypted with session key

                    // Check for login
                    LdapConnection conn = SessionServices.login_for_ldap_user(user.username,
                                                                              plain_text_old_password);
                    // If it gets logged in
                    if (conn != null && conn.isAuthorized()) {
                        try {
                            conn.change_user_password(plain_text_old_password,
                                                      plain_text_new_password);
                        }
                        catch (InvalidValueException _ive) {
                            response.setError(_ive.getMessage());
                        }
                        conn.close();
                    } else {
                        response.setError("Invalid credentials");
                    }
                } else {

                    String old_password = FHashGenerator.generate(plain_text_old_password);

                    List res = em.createQuery("SELECT u FROM EUser u WHERE " +
                                              " u.id = :userId AND u.password = :password and u.externalUser = false")
                                 .setParameter("userId",
                                               session.user_id)
                                 .setParameter("password",
                                               old_password)
                                 .getResultList();

                    if (res.size() > 0) {
                        String new_password = FHashGenerator.generate(plain_text_new_password);
                        em.getTransaction()
                          .begin();
                        user.password = new_password;
                        em.merge(user);
                        em.getTransaction()
                          .commit();
                    } else {
                        response.setError("Invalid current password");
                    }


                }
            }


        } catch (InvalidValueException _ive) {
            Warning.get_instance().print(_ive);
            response.setError(_ive.getMessage());

        }
        catch (Exception e) {
            Warning.get_instance().print(e);
            response.setError("Could not change password");
        }

        return Response.ok().entity(response).build();
    });

}





}
