package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.Crypto;
import com.sceptive.forgiva.integrator.core.crypto.FHashGenerator;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.core.db.objects.EUser;
import com.sceptive.forgiva.integrator.core.db.objects.EUserGroup;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.impl.CommonOperations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/*
   This class is responsible with bootstrapping database connection and entity schema
*/
public class BSDatabase extends IBootStrap {


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
