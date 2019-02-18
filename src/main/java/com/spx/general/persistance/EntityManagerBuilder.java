package com.spx.general.persistance;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.spx.general.config.ApplicationConfiguration;
import com.spx.session.AuthorityAccess;

import io.dropwizard.db.DataSourceFactory;

public class EntityManagerBuilder {
    
    private static final String DEFAULT_PU = "myPU";

    protected  ApplicationConfiguration configuration;

    @Inject  
    public EntityManagerBuilder(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    private Map<String, String> getDatabasConfiguration(DataSourceFactory database) {
        Map<String, String> persistenceMap = new HashMap<String, String>(database.getProperties());
        persistenceMap.put("javax.persistence.jdbc.url", database.getUrl());
        persistenceMap.put("javax.persistence.jdbc.user", database.getUser());
        persistenceMap.put("javax.persistence.jdbc.password", database.getPassword());
        persistenceMap.put("javax.persistence.jdbc.driver", database.getDriverClass());
        persistenceMap.put("show_sql", "true");
        persistenceMap.put("format_sql", Boolean.TRUE.toString());
        persistenceMap.put("hibernate.ejb.resource_scanner",com.spx.session.AuthorityEntityScanner.class.getName().toString());
        persistenceMap.put("hibernate.archive.autodetection", "class");
        return persistenceMap;
    }

    @Produces 
    public EntityManager getEntityManager() {
        EntityManager result = doCreatEntitityManagerFromPersitanceUnit(DEFAULT_PU,configuration.getDatabase());
        return result;
    }
    
    @Produces
    @AuthorityAccess
    public EntityManager getAuthorityEntityManager() {
        return getEntityManager();
    }
  
    private EntityManager createEntityManager(String persistanceUnitName,Map<String, String> persistenceMap) {
        HibernatePersistenceProvider pp = new HibernatePersistenceProvider();
        EntityManagerFactory factory = pp.createEntityManagerFactory(persistanceUnitName, persistenceMap);
        return factory.createEntityManager();
    }

    private EntityManager doCreatEntitityManagerFromPersitanceUnit(String persistanceUnitName, DataSourceFactory database) {
        Map<String, String> persistenceMap = getDatabasConfiguration(database);
        return createEntityManager(persistanceUnitName,persistenceMap);      
    }
}