package com.spx.containment.persistance;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.deltaspike.data.api.EntityManagerResolver;
import com.spx.general.persistance.FilteredEntityScanner;

public class ContainerEntityScanner extends FilteredEntityScanner  implements  EntityManagerResolver {

   
    @Inject @ContainmentAccess
    private EntityManager  entityManager;
    
    public Class<? extends Annotation> getEntityGroup() {
        return ContainmentAccess.class;
    }

    @Override
    public EntityManager resolveEntityManager() {
        return entityManager;
    }
  
}
