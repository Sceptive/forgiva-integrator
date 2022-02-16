package com.sceptive.forgiva.integrator.core.db;

import com.sceptive.forgiva.integrator.logging.Debug;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SynchronizationType;
import javax.persistence.spi.PersistenceUnitInfo;



public final class DataProvider {

    private static final String PROPERTY_PERSISTENCE_PASSWORD = "javax.persistence.jdbc.password";
    private static final String PROPERTY_PERSISTENCE_USER = "javax.persistence.jdbc.user";
    private static final String PROPERTY_PERSISTENCE_URL = "javax.persistence.jdbc.url";
    private static final String PROPERTY_PERSISTENCE_DRIVER = "javax.persistence.jdbc.driver";

    // EclipseLink specific properties

    private String persistenceUnitName = "default";
    private final String driver;
    private final String url;
    private final String user;
    private final String password;
    private final List<Class<?>> entities;

    private EntityManagerFactory entityManagerFactory;

    public DataProvider(String driver, String url, String user, String password,
                           Class<?>... entities) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        this.entities = new ArrayList<>(Arrays.asList(entities));

    }

    public DataProvider setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
        return this;
    }


    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = createEntityManagerFactory();
        }
        return entityManagerFactory;
    }

    private EntityManagerFactory createEntityManagerFactory() {
        try {



            Debug.get_instance().print("Creating entity manager.");
            ClassLoader modelClassLoader = entities.get(0).getClassLoader();
            Map<String, Object> properties = new HashMap<>();
            properties.put(PersistenceUnitProperties.JDBC_DRIVER, driver);
            properties.put(PersistenceUnitProperties.JDBC_URL, url);
            properties.put(PersistenceUnitProperties.JDBC_USER, user);
            properties.put(PersistenceUnitProperties.JDBC_PASSWORD, password);
            properties.put(PersistenceUnitProperties.SESSION_CUSTOMIZER,
                    "com.sceptive.forgiva.integrator.core.db.DBSessionCustomizer");
            properties.put(PersistenceUnitProperties.LOGGING_LOGGER,
                    "com.sceptive.forgiva.integrator.logging.EclipseLinkLog4j2Bridge");


            //properties.put("eclipselink.target-database","Derby");
            properties.put("eclipselink.ddl-generation",PersistenceUnitProperties.CREATE_OR_EXTEND);
            properties.put("eclipselink.debug","ALL");
            //properties.put("eclipselink.weaving","static");
            properties.put("eclipselink.logging.level","FINEST");
            properties.put("eclipselink.logging.level.sql","FINEST");
            properties.put("eclipselink.logging.level.cache","FINEST");

            PersistenceUnitInfo persistenceUnitInfo = new DefaultPersistenceUnitInfo(persistenceUnitName,
                    modelClassLoader);

            Debug.get_instance().print("Creating entity manager factory.");

            EntityManagerFactory emf = new PersistenceProvider()
                    .createContainerEntityManagerFactory(persistenceUnitInfo, properties);



            Debug.get_instance().print("Entity manager factory launched.");



            return emf;


        } catch (Exception e) {

            Fatal.get_instance().print(e);
            return null;
        }
    }
}