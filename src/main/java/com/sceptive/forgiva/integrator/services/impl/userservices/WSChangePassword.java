package com.sceptive.forgiva.integrator.services.impl.userservices;

import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.PasswordGuard;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.FHashGenerator;
import com.sceptive.forgiva.integrator.core.db.objects.EUser;
import com.sceptive.forgiva.integrator.core.ldap.LdapConnection;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserPasswordChangeRequest;
import com.sceptive.forgiva.integrator.services.impl.SessionServices;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WSChangePassword {
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
