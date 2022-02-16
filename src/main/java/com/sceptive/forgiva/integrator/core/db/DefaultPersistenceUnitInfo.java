package com.sceptive.forgiva.integrator.core.db;

import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.logging.Info;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DefaultPersistenceUnitInfo implements PersistenceUnitInfo {

    private static final String PERSISTENCE_PROVIDER = "org.eclipse.persistence.jpa.PersistenceProvider";
    private final String        persistenceUnitName;
    private final ClassLoader   classLoader;

    public DefaultPersistenceUnitInfo(String _persistenceUnitName,
                                      ClassLoader _classLoader) {
        persistenceUnitName = _persistenceUnitName;
        classLoader         = _classLoader;
    }
    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return PERSISTENCE_PROVIDER;
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return Collections.emptyList();
    }

    @Override
    public List<URL> getJarFileUrls() {
        return Collections.emptyList();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        try {
            return new File(".").toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<String> getManagedClassNames() {


        List<String> ret = new ArrayList<>();

        for (Class c : Database.entity_classes) {
            ret.add(c.getName());
        }

        return ret;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return true;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public Properties getProperties() {
        return new Properties();
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

       // throw new UnsupportedOperationException();
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return classLoader;
    }
}
