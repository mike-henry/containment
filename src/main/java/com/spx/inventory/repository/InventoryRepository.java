package com.spx.inventory.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

import com.spx.containment.model.Container;
import com.spx.containment.persistance.ContainerEntityScanner;
import com.spx.containment.persistance.ContainmentAccess;
import com.spx.containment.persistance.ModelRepository;
import com.spx.inventory.model.Inventory;
@Repository

@EntityManagerConfig(entityManagerResolver = ContainerEntityScanner.class, flushMode = FlushModeType.COMMIT)
public abstract class InventoryRepository extends AbstractEntityRepository<Inventory, UUID>  {

    @Inject
    private ModelRepository saver;
    
    @Inject
    @ContainmentAccess
    private EntityManager em; 
    

    @Query("select i from Inventory i where i.name=:name")
    public abstract Optional<Inventory> findByName(@QueryParam("name") String name);

    @Query("select i from Inventory i where i.id = :id")
    public abstract Optional<Inventory> findById(@QueryParam("id") String id);

    
    
    @SuppressWarnings("unchecked")
    public  List<Inventory> findByContainers(Collection<Container> containers){
       String query = "{"+getContainerNativeCriteria(containers) +"}";
       return em.createNativeQuery(query,Inventory.class)
        .getResultList();
    }

    private String getContainerNativeCriteria(Collection<Container> containers) {
        String containerCriteria= containers.stream()
        .map(c ->c.getId().toString())
        .map(id -> "\""+id+"\"")
        .map( id -> String.format("{container_id:%s}",id))
        .collect(Collectors.joining(",\n", "$or:  [", "]"));
        String query = String.format("%s",containerCriteria);
        return query;
    }
    
    
   
    @Override
    public Inventory save(Inventory inventory) {
        return saver.save(inventory);
    }

    public Collection<Inventory> findByContainer(Container container) {
        return this.findByContainers(Collections.singleton(container));
    }

}