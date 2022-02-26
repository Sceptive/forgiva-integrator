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
                    _request.getValue().trim().length() == 0
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

                EntityManager em     = Database.get_instance().getEm();
                EUserSetting usetting;
                try {
                    usetting = em.createQuery(
                                    "SELECT m FROM EUserSetting m WHERE " + " m.userId = :userId " +
                                            " AND m.key = :key", EUserSetting.class)
                            .setParameter("userId", session.user_id)
                            .setParameter("key", key)
                            .getSingleResult();

                    usetting.value   = save_value;
                    em.getTransaction().begin();
                    em.merge(usetting);
                    em.getTransaction().commit();
                }
                 catch (NoResultException _nre) {
                     usetting         = new EUserSetting();
                     usetting.userId  = session.user_id;
                     usetting.key     = key;
                     usetting.value   = save_value;

                     em.getTransaction().begin();
                     em.persist(usetting);
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


    public static String settings_get_ex(long _userId, String _key) throws InvalidValueException {


        String ret;

        final Constants.user_setting setting;

        if ((setting = Constants.setting_by_name(_key)) == null) {
            throw new InvalidValueException("Request contains invalid data");
        }

        EntityManager em = Database.get_instance().getEm();

        try {

            EUserSetting usetting = em.createQuery(
                            "SELECT m FROM EUserSetting m WHERE " + " m.userId = :userId " +
                                    " AND m.key = :key", EUserSetting.class)
                    .setParameter("userId", _userId)
                    .setParameter("key", _key)
                    .getSingleResult();

            ret = usetting.value;

        } catch (NoResultException _nre) {
            ret = setting.default_value;
        }

        return ret;
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
