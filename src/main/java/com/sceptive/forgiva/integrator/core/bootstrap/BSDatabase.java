package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.core.db.objects.EUserGroup;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;

import javax.persistence.EntityManager;

/*
   This class is responsible with bootstrapping database connection and entity schema
*/
public class BSDatabase extends IBootStrap {

  private void initialize_template_data() {

    // If there is no user group. Create one.
    EntityManager em = Database.get_instance().getEm();

    Long get_usergroup_counts =
        (Long) em.createQuery("SELECT COUNT(u) FROM EUserGroup u").getSingleResult();

    if (get_usergroup_counts == 0) {
      EUserGroup default_user_group = new EUserGroup();
      default_user_group.userGroupName      = Constants.DEFAULT_USERGROUP_NAME;
      default_user_group.groupDescription   = Constants.DEFAULT_USERGROUP_DESCRIPTION;
      default_user_group.id                 = 0;

      em.getTransaction().begin();
      em.persist(default_user_group);
      em.getTransaction().commit();
      em.close();
    }
  }

  @Override
  public boolean bootstrap(Parameters _launch_options) {
    // If not initialize then bootstrap failed
    if (Database.get_instance() == null) {
      Fatal.get_instance().print("Could not initialize database connection over JDBC.");
    }

    Info.get_instance().print("Database connection bootstrapped successfully.");

    return true;
  }
}
