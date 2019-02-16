package com.spx.containment.chain;

import java.util.List;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.spx.containment.chain.model.Allocation;
import com.spx.containment.chain.model.BOMItem;
import com.spx.containment.chain.model.Request;
import com.spx.containment.chain.model.SupplyChainLink;
import com.spx.containment.chain.repository.InventoryAllocationRepository;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.services.InventoryLedger;


//@RequestScoped
@Named
public class InventoryRequestAllocationService {
 
    private final InventoryLedger ledger;
    private final SupplyChainService supplyChainService;
	private final InventoryAllocationRepository inventoryAllocationRepository;
    
    @Inject
    public InventoryRequestAllocationService(InventoryLedger ledger,
    		SupplyChainService supplyChainService,
    		InventoryAllocationRepository inventoryAllocationRepository
    		){
         this.ledger =ledger;        
         this.supplyChainService=supplyChainService;
         this.inventoryAllocationRepository=inventoryAllocationRepository;
    }
   
   public void allocateTo(final Request request){
       request.getRequiredItems().stream()
       .forEach(item ->  allocateInventoryTo(request, item)
       );
   }
  
   private void allocateInventoryTo(final Request request,BOMItem item) {
       SupplyChainLink chain = supplyChainService.getSupplyChainFor(request.getDestination(),item.getProduct()).orElseThrow( () ->new RuntimeException("No supply chain found"));
       List<Inventory> inventoryOfProduct = ledger.getContainerContentsOfProduct(chain.getFrom(), item.getProduct()) ;
       int alloctedSoFar = 0;
       
       for(Inventory inventory:inventoryOfProduct) {
           if (alloctedSoFar < item.getQuantity()) {
               int free = getFreeQuantity(inventory);
               int allocationQuanity = Math.min(item.getQuantity() - alloctedSoFar, free);
               if (allocationQuanity == 0 ) continue;
               createAllocation(request, inventory, allocationQuanity);
               alloctedSoFar+=allocationQuanity;
           }
       }
   }
  
   private void createAllocation(final Request request, Inventory inventory, int quanity) {
       Allocation.Builder builder = new Allocation.Builder();
	   Allocation allocation = builder
	   	   .inventory(inventory)
		   .request(request)
		   .quantity(quanity)
		   .build();
	   allocation=   inventoryAllocationRepository.save(allocation);
	   request.getAllocations().add(allocation);
   }

    private int getFreeQuantity(Inventory inventory) {
         int totalAllocated=inventoryAllocationRepository.findByInventory(inventory)
         .stream()
         .mapToInt(a -> a.getQuantity())
         .sum();
       return inventory.getQuantity() - totalAllocated;
    }
    
    public Stream <Allocation> getAllocations(Request request){
       return request.getAllocations().stream();
    }

}