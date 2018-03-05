package com.spx.containment.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.spx.containment.chain.SupplyChainService;
import com.spx.containment.chain.model.SupplyChainLink;
import com.spx.containment.model.Container;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.services.InventoryLedger;
import com.spx.product.model.Product;

public class DemandAllocationService {
    
  
    /// this is not real...yet
    List<Allocation> allocations = new ArrayList<Allocation>();

    private final InventoryLedger ledger;
    private final SupplyChainService supplyChainService;
    
    public DemandAllocationService(InventoryLedger ledger,SupplyChainService supplyChainService){
         this.ledger =ledger;        
         this.supplyChainService=supplyChainService;
    }
    
    
    
   public void allocateTo(final Request request){
       request.getRequiredItems().stream()
       .forEach(item ->  allocateInventoryTo(request, item)
       );
   }
   
   
   
   
   public void allocateInventoryTo(final Request request,BOMItem item) {
       SupplyChainLink chain = supplyChainService.getSupplyChainFor(request.getDestination(),item.getProduct()).orElseThrow( () ->new RuntimeException("No supply chain found"));
       Stream<Inventory> inventoryOfProduct = ledger.getContainerContentsOfProduct(chain.getFrom(), item.getProduct()) ;
       AtomicInteger alloctedSoFar = new AtomicInteger(0);
       inventoryOfProduct
       .forEach(i -> {
           if (alloctedSoFar.get() < item.getQuantity()) {
               int free = getFreeQuantity(i);
               int allocationQuanity = Math.min(item.getQuantity() - alloctedSoFar.get(), free);
               Allocation allocation = new Allocation( request.getDestination(), allocationQuanity, i,request);
               request.getAllocations().add(allocation);
               alloctedSoFar.addAndGet(allocationQuanity);
           }
       });
   }

   
   public void allocateInventoryFor(Container to, int requiredQuantity, Product product, String name) {
          
        SupplyChainLink chain = supplyChainService.getSupplyChainFor(to,product).orElseThrow( () ->new RuntimeException("No supply chain found"));
        Stream<Inventory> inventoryOfProduct = ledger.getContainerContentsOfProduct(chain.getFrom(), product) ;
       
        AtomicInteger alloctedSoFar = new AtomicInteger(0);
        inventoryOfProduct
        .forEach(i -> {
            if (alloctedSoFar.get() < requiredQuantity) {
                int free = getFreeQuantity(i);
                int allocationQuanity = Math.min(requiredQuantity - alloctedSoFar.get(), free);
                
                Allocation allocation = new Allocation( to, allocationQuanity, i,null);
                
                this.allocations.add(allocation);
                alloctedSoFar.addAndGet(allocationQuanity);
            }
        });
    }

    private int getFreeQuantity(Inventory inventory) {
        int totalAllocated=this.allocations.stream()
           .filter(a -> a.getInventory().equals(inventory))
           .mapToInt(a -> a.getQuantity())
           .sum();
        return inventory.getQuantity() - totalAllocated;
    }
    
    public Stream <Allocation> getAllocations(String name){
        return this.allocations.stream()
        .filter(a -> a.getTo().getName().equals(name));
    }

}


