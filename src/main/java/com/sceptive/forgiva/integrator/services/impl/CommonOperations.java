package com.sceptive.forgiva.integrator.services.impl;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadataGroup;

import javax.persistence.EntityManager;

public class CommonOperations
{
    public static boolean create_default_metadatagroup_for_user(long _user_id) {
        // If user removes all records then create one default record
        EntityManager em = Database.get_instance().getEm();
        Long get_metadatagroup_counts =
                (Long)
                        em.createQuery(
                                "SELECT COUNT(u) FROM EMetadataGroup u WHERE u.userId = :userId")
                                .setParameter("userId", _user_id)
                                .getSingleResult();
        // If there is no metadata group for user.
        if (get_metadatagroup_counts == 0) {

            EMetadataGroup default_metadata_group = new EMetadataGroup();
            default_metadata_group.groupName = Constants.DEFAULT_METADATAGROUP_NAME;
            default_metadata_group.groupDescription = Constants.DEFAULT_METADATAGROUP_DESCRIPTION;
            default_metadata_group.userId = _user_id;


            em.getTransaction().begin();
            em.persist(default_metadata_group);
            em.getTransaction().commit();
            em.close();
            return true;
        }

        return false;
    }
}
