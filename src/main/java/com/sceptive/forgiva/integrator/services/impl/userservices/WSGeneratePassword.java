package com.sceptive.forgiva.integrator.services.impl.userservices;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.ForgivaServerInvoker;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.core.db.objects.EUser;
import com.sceptive.forgiva.integrator.core.fsconnectors.IFSResponse;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserGenerateRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserGenerateResponse;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

public class WSGeneratePassword {
    /*

     */
    public static Response generate_password(PostUserGenerateRequest _postUserGenerateRequest)
            throws NotFoundException {
        return SecurityManager
                .secure_invoke(_postUserGenerateRequest.getHeader(), false, (session) -> {

                    PostUserGenerateResponse ret = new PostUserGenerateResponse();

                    try {

                        EntityManager em = Database.get_instance().getEm();


                        byte[] decrypted_masterkey_hash;

                        /*
                            If there is no master key and if user set to use login password as master key then
                            fetch the password hash from database and use it as master key
                         */
                        if (_postUserGenerateRequest.getMasterKey() == null) {

                            boolean use_password_as_masterkey =
                                    Boolean.parseBoolean(WSUserSettings
                                            .settings_get_ex(session.user_id, Constants.CONST_US_KEY_MUI));

                            if (!use_password_as_masterkey) {
                                throw new Exception("No master key");
                            }

                            EUser u = (EUser) em.createQuery("SELECT u FROM EUser u WHERE " +
                                            " u.id = :id ")
                                    .setParameter("id",
                                            session.user_id)
                                    .getSingleResult();

                            assert(u != null);

                            decrypted_masterkey_hash = Common.decodeHex(u.password);

                        } else {

                            decrypted_masterkey_hash = Asymmetric
                                    .decrypt_using_session(Common.decodeHex(_postUserGenerateRequest.getMasterKey()), session);

                        }


                        /**
                         * Get full metadata from database
                         */
                        Long mId            = Long.parseLong(_postUserGenerateRequest.getMetadataId());
                        EMetadata metadata  = em.createQuery(
                                "SELECT m FROM EMetadata m WHERE " + " m.userId = :userId " +
                                " AND m.id = :mId", EMetadata.class)
                                                .setParameter("userId", session.user_id)
                                                .setParameter("mId", mId)
                                                .getSingleResult();

                        boolean[]       executed    = new boolean[] { false };
                        boolean[]       has_error   = new boolean[] { false };
                        final String[]  generated    = new String[] { ""};

                        // Update state if wanted
                        // TODO: Maybe renewal metadata history is needed
                        if (_postUserGenerateRequest.getRenewalDate() != null) {
                            metadata.lastRenewal = _postUserGenerateRequest.getRenewalDate();
                        }

                        if (metadata.lastRenewal == null) {
                            metadata.lastRenewal = "";
                        }

                        metadata.generatedBefore = true;
                        em.getTransaction().begin();
                        em.merge(metadata);
                        em.getTransaction().commit();
                        em.close();

                        ret.setResult(new OperationResult().info("ok"));

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

                        byte[] encrypted_password = Asymmetric
                                                    .encrypt_using_session( Common.decodeHex(generated[0]),session);

                        ret.setGeneratedPassword(Common.encodeHex(encrypted_password));

                    } catch (Exception ex) {
                        ret.setResult(new OperationResult().error("Invalid data"));
                        Warning.get_instance().print(ex);
                    }

                    return Response.ok().entity(ret).build();
                });
    }
}
