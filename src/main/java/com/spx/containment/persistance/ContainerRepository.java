package com.spx.containment.persistance;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.FlushModeType;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

import com.spx.containment.model.Container;
@Repository
@EntityManagerConfig(entityManagerResolver =  ContainerEntityScanner.class, flushMode = FlushModeType.AUTO)
public abstract class ContainerRepository  extends AbstractEntityRepository<Container, UUID>{
    
    @Inject
    private ModelRepository saver;

    @Query("select c from Container c where c.name=:name")
    public abstract Optional<Container> findByName(@QueryParam("name")String name);
    
    @Query("select c from Container c where c.reference=:reference")
    public abstract Optional<Container> findByReference(@QueryParam("reference")String reference);
    
    @Query("select c from Container c where c.id = :id")
    public abstract  Optional<Container> findById(@QueryParam("id")UUID id);
  
    @Override
    public Container save(Container container) {
        return saver.save(container);
    }
}