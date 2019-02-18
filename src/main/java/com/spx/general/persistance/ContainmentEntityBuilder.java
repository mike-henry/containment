package com.spx.general.persistance;

import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.ogm.cfg.OgmProperties;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.spx.containment.persistance.ContainmentAccess;
import com.spx.general.PeristanceConfiguration;
import com.spx.general.config.ApplicationConfiguration;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ContainmentEntityBuilder extends EntityManagerBuilder {

    @Inject
    @ContainmentAccess
    public ContainmentEntityBuilder(ApplicationConfiguration configuration) {
        super(configuration);
    }

    @Produces
    @ContainmentAccess
    @RequestScoped
    public EntityManager getContainmentEntityManager() {
        return getContainmentEntityManagerFactory().createEntityManager();
    }

    @Produces
    @ContainmentAccess
    public EntityManagerFactory getContainmentEntityManagerFactory() {
    	PeristanceConfiguration dbConfig = configuration.getContainment().getDatabase();
        Properties persistanceUnitProperties = new Properties();
        persistanceUnitProperties.put(OgmProperties.ENABLED, Boolean.TRUE.toString());
        persistanceUnitProperties.put(OgmProperties.DATASTORE_PROVIDER,"neo4j_bolt");
        persistanceUnitProperties.put("hibernate.cache.use_structured_entries", "true");
        persistanceUnitProperties.put(OgmProperties.HOST,dbConfig.getUrl());
        persistanceUnitProperties.put(OgmProperties.USERNAME,dbConfig.getUser());
        persistanceUnitProperties.put(OgmProperties.PASSWORD,dbConfig.getPassword());       
        persistanceUnitProperties.put(OgmProperties.DATABASE, configuration.getContainment().getDatabaseName());
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(dbConfig.getPersistanceUnit(), persistanceUnitProperties);
        return factory;
    }

}
