package com.spx.sample.persistance;

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
import com.spx.sample.ApplicationConfiguration;

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
        Properties persistanceUnitProperties = new Properties();
        persistanceUnitProperties.put(OgmProperties.ENABLED, Boolean.TRUE.toString());
        persistanceUnitProperties.put(OgmProperties.DATASTORE_PROVIDER,"neo4j_bolt");
        persistanceUnitProperties.put("hibernate.cache.use_structured_entries", "true");
        persistanceUnitProperties.put(OgmProperties.HOST,"localhost:7687");
        persistanceUnitProperties.put(OgmProperties.USERNAME,"neo4j");
        persistanceUnitProperties.put(OgmProperties.PASSWORD,"ignit10n");
        persistanceUnitProperties.put(OgmProperties.DATABASE, configuration.getContainment().getDatabaseName());
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(configuration.getContainment().getPersistanceUnit(), persistanceUnitProperties);
        return factory;
    }

}
