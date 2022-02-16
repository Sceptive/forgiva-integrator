package com.sceptive.forgiva.integrator.core;

import com.sceptive.forgiva.integrator.core.db.DataProvider;
import com.sceptive.forgiva.integrator.core.db.objects.*;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Debug;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/*
    Core Database object provides EntityManager to run database operations.
 */
public class Database {

/* ***************************************************************************

    STATIC METHODS

*************************************************************************** */
    private static Database instance;

    /*
        Returns instance if not initialized statically
     */
    public static Database get_instance() {
        if (instance == null) {
            try {
                instance = new Database();
            } catch (NotInitializedException ex) {
                return null;
            }
        }

        return instance;
    }



    public static Class<?>[] entity_classes = {
            ESession.class,
            EUser.class,
            EUserGroup.class,
            EMetadataGroup.class,
            EMetadata.class,
            EStatistics.class,
            EUserSetting.class,
            ELoginLog.class,
            EHost.class,
            EApplication.class

    };
/* ***************************************************************************

    CLASS METHODS

*************************************************************************** */


    private DataProvider         dp;
    private EntityManagerFactory emf;

    /*
        Constructs database object and throws NotInitializedException
        if database connection could not created successfully.
     */
    public Database() throws NotInitializedException {
        if (!initialize()) {
            throw new NotInitializedException();
        }
    }

    /*
        Returns EntityManager object
     */
    public EntityManager    getEm() {
        return emf.createEntityManager();
    }

    /*
        Returns DataProvider object
     */
    public DataProvider     getDp() {
        return dp;
    }

    /*
        Initializes database connection and EntityManager (em) and
        returns true if all succeeds with no problem.
     */
    private boolean initialize() {


        Debug.get_instance().print("Initializing data provider...");
        dp = new DataProvider(
                Configuration.runtime.db_jdbc_driver,
                Configuration.runtime.db_jdbc_url,
                Configuration.runtime.db_jdbc_user,
                Configuration.runtime.db_jdbc_password,
                entity_classes
        );


        Debug.get_instance().print("Initializing entity manager");
        emf = dp.getEntityManagerFactory();

        // Return false if EntityManager is null or not opened
        if (emf == null || !emf.isOpen()) {
            Debug.get_instance().print("Entity manager  factory could not initialized");
            return false;
        }

        Debug.get_instance().print("Database initialized successfully.");
        return true;
    }
}
