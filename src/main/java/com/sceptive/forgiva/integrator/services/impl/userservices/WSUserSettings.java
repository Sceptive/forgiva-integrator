package com.sceptive.forgiva.integrator.services.impl.userservices;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.db.objects.EUserSetting;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.model.OperationResult;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserSettingsGetRequest;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserSettingsGetResponse;
import com.sceptive.forgiva.integrator.services.gen.model.PostUserSettingsSetRequest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;

public class WSUserSettings {


    public static boolean settings_set_ex(long _userId,
                                            String _key,
                                            String _new_value) throws Exception {


        EntityManager em = Database.get_instance().getEm();
        EUserSetting usetting;
        try {
            usetting = em.createQuery(
                            "SELECT m FROM EUserSetting m WHERE " + " m.userId = :userId " +
                                    " AND m.key = :key", EUserSetting.class)
                    .setParameter("userId", _userId)
                    .setParameter("key", _key)
                    .getSingleResult();

            usetting.value   = _new_value;
            em.getTransaction().begin();
            em.merge(usetting);
            em.getTransaction().commit();
        }
        catch (NoResultException _nre) {
            usetting         = new EUserSetting();
            usetting.userId  = _userId;
            usetting.key     = _key;
            usetting.value   = _new_value;

            em.getTransaction().begin();
            em.persist(usetting);
            em.getTransaction().commit();
        }


        em.close();


        return true;
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

                final String key                        = _request.getKey();
                final Constants.user_setting setting;


                if (key == null ||
                    _request.getValue() == null ||
                        ((setting = Constants.setting_by_name(key)) == null) ||
                    _request.getValue().trim().length() == 0 ||
                        setting.hidden ||
                        setting.readonly
                    ) {
                    throw new InvalidValueException("Request contains invalid data");
                }

                String save_value = _request.getValue();

                // Validating values. Important for security.
                if (setting.type.getName().equalsIgnoreCase(Boolean.class.getName())) {
                   save_value = Boolean.toString(Boolean.parseBoolean(save_value));
                } else if (setting.type.getName().equalsIgnoreCase(Integer.class.getName())) {
                    save_value = Integer.toString(Integer.parseInt(save_value));
                }

                settings_set_ex(session.user_id, key, save_value);

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
     * Returns value within database.
     *
     * @param _userId
     * @param _key
     * @return
     * @throws InvalidValueException
     */
    public static String settings_get_ex_ex(long _userId, String _key) throws InvalidValueException {
        String ret          = null;
        EntityManager em    = Database.get_instance().getEm();

        try {

            EUserSetting usetting = em.createQuery(
                            "SELECT m FROM EUserSetting m WHERE " + " m.userId = :userId " +
                                    " AND m.key = :key", EUserSetting.class)
                    .setParameter("userId", _userId)
                    .setParameter("key", _key)
                    .getSingleResult();

            ret = usetting.value;

        } catch (NoResultException _nre) {

        }

        em.close();

        return ret;
    }

    /**
     *
     * Returns value within database with additional checks such as read-only settings
     *
     * @param _userId
     * @param _key
     * @return
     * @throws InvalidValueException
     */
    public static String settings_get_ex(long _userId, String _key) throws InvalidValueException {


        String ret;

        final Constants.user_setting setting;

        if ((setting = Constants.setting_by_name(_key)) == null ||
                setting.hidden) {
            throw new InvalidValueException("Request contains invalid data");
        }

        /**
         * For READ-ONLY properties only.
         *
         * If remote checking for 2fa_enabled then check if sotp code is valid or not
         */
        if (_key.contentEquals(Constants.CONST_US_2FA_ENB)) {

            String sotp_code = settings_get_ex_ex(_userId, Constants.CONST_US_2FA_COD);
            ret = (sotp_code != null && !sotp_code.isEmpty()) ? "true" : "false";

        } else {
            ret = settings_get_ex_ex(_userId, _key);
        }

        return ret == null ? setting.default_value : ret;
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

                if (key == null)
                {
                    throw new InvalidValueException("Request contains invalid data");
                }

                response.setValue(settings_get_ex(session.user_id, key));

            } catch (Exception e) {
                Warning.get_instance().print(e);
                response.setValue(null);
            }


            return Response.ok().entity(response).build();
        });


    }
}
