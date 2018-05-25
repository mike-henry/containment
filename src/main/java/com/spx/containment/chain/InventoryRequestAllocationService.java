package com.spx.containment.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.spx.containment.chain.model.Allocation;
import com.spx.containment.chain.model.BOMItem;
import com.spx.containment.chain.model.Request;
import com.spx.containment.chain.model.SupplyChainLink;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.services.InventoryLedger;


@RequestScoped
public class InventoryRequestAllocationService {
    
  
    /// this is not real...yet
    List<Allocation> allocations = new ArrayList<Allocation>();

    private final InventoryLedger ledger;
    private final SupplyChainService supplyChainService;
    
    @Inject
    public InventoryRequestAllocationService(InventoryLedger ledger,SupplyChainService supplyChainService){
         this.ledger =ledger;        
         this.supplyChainService=supplyChainService;
    }
   
   public void allocateTo(final Request request){
       request.getRequiredItems().stream()
       .forEach(item ->  allocateInventoryTo(request, item)
       );
   }

   
   @Deprecated
   public void allocateInventoryTo(final Request request,BOMItem item) {
       SupplyChainLink chain = supplyChainService.getSupplyChainFor(request.getDestination(),item.getProduct()).orElseThrow( () ->new RuntimeException("No supply chain found"));
       Stream<Inventory> inventoryOfProduct = ledger.getContainerContentsOfProduct(chain.getFrom(), item.getProduct()) ;
       AtomicInteger alloctedSoFar = new AtomicInteger(0);
       inventoryOfProduct
       .forEach(inventory -> {
           if (alloctedSoFar.get() < item.getQuantity()) {
               int free = getFreeQuantity(inventory);
               int allocationQuanity = Math.min(item.getQuantity() - alloctedSoFar.get(), free);
               createAllocation(request, inventory, allocationQuanity);
               alloctedSoFar.addAndGet(allocationQuanity);
           }
       });
   }

private void createAllocation(final Request request, Inventory inventory, int quanity) {
	Allocation.Builder builder = new Allocation.Builder();
	Allocation allocation = builder
	.inventory(inventory)
	.request(request)
	.quantity(quanity)
	.build();
    request.getAllocations().add(allocation);
}

    private int getFreeQuantity(Inventory inventory) {
        int totalAllocated=this.allocations.stream()
           .filter(a -> a.getInventory().equals(inventory))
           .mapToInt(a -> a.getQuantity())
           .sum();
        return inventory.getQuantity() - totalAllocated;
    }
    
    public Stream <Allocation> getAllocations(Request request){
    	return request.getAllocations().stream();
    			
    }

}


