package com.spx.containment.chain.repository;

import java.util.Collection;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;
import com.spx.containment.chain.model.Allocation;
import com.spx.containment.persistance.ContainerEntityScanner;
import com.spx.containment.persistance.ContainmentAccess;
import com.spx.containment.persistance.ModelRepository;
import com.spx.inventory.model.Inventory;
@Repository

@EntityManagerConfig(entityManagerResolver = ContainerEntityScanner.class, flushMode = FlushModeType.COMMIT)
public abstract class InventoryAllocationRepository extends AbstractEntityRepository<Allocation, UUID>  {

    @Inject
    private ModelRepository saver;
    
    @Inject
    @ContainmentAccess
    private EntityManager em; 
    

    @Query("select  Allocation a where a.inventory=inventory")
    public abstract Collection<Allocation>  findByInventory(@QueryParam("inventory")Inventory inventory);
    
   
  
	public Allocation save(Allocation allocation) {
		if (allocation.getId() != null) {
			allocation = em.merge(allocation);
		} else if (!em.contains(allocation)) {
			em.persist(allocation);
		}
		return allocation;

	}


}