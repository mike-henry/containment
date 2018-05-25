package com.spx.containment.chain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.chain.model.Allocation;
import com.spx.containment.chain.model.BOMItem;
import com.spx.containment.chain.model.Request;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.repository.InventoryRepository;
import com.spx.inventory.services.InventoryLedger;
import com.spx.product.model.Product;

public class InventoryRequestTest {

    Container from = new Container();
    Container to = new Container();
    private InventoryRepository repository;
    InventoryLedger ledger = new InventoryLedger();
    SupplyChainService supplyChainService;
    InventoryRequestAllocationService subject;

    
    @Before
    public void init(){
        repository = mock(InventoryRepository.class);
        from.setName("from");
        to.setName("to");
        ledger = new InventoryLedger(repository,new ContainerServices());
        supplyChainService = new SupplyChainService(ledger);
        subject = new InventoryRequestAllocationService(ledger, supplyChainService);
    }
    
    @Test
    public void createADemand() {
        List<Inventory> mockInventory = new ArrayList<Inventory>();
        when(repository.findByContainer(any(Container.class))).thenReturn(mockInventory);
        int wantedQuantity = 10;
        Product wantedProduct1 = new Product();
        wantedProduct1.setDescription("widget-alpha");
        wantedProduct1.setReference("widget-a");
        
     
        Product wantedProduct2 = new Product();
        wantedProduct2.setDescription("widget-beta");
        wantedProduct2.setReference("widget-b");
        Inventory inventory = new Inventory(null, 99, wantedProduct1,"dummy");
        ledger.addToContainer(from, inventory);
        supplyChainService.addSupplyLink(wantedProduct1, from, to);
        supplyChainService.addSupplyLink(wantedProduct2, from, to);
        Request request = new Request.Builder("d1")
       		 .destination(to)
       		 .build();
        BOMItem requiredItem =  new BOMItem(wantedProduct1,wantedQuantity);
       
        subject.allocateInventoryTo(request, requiredItem);

        Stream<Allocation> allocations = subject.getAllocations(request);
        allocations.forEach(a -> {
            assertEquals(a.getQuantity(), wantedQuantity);
        });
  
    }

    @Test
    public void createADemandOfChild() {
        
        String  demandName = "d1";

        final int wantedQuantity = 10;
        Product wantedProduct1 = new Product();
        wantedProduct1.setDescription("widget-alpha");
        wantedProduct1.setReference("widget-a");
        Product wantedProduct2 = new Product();
        wantedProduct2.setDescription("widget-beta");
        wantedProduct2.setReference("widget-b");
        
  
        BOMItem requiredItem =  new BOMItem(wantedProduct1,wantedQuantity);
        

        List<Inventory> inventoryFound = new ArrayList<Inventory>();
        when(repository.findByContainer(from)).thenReturn(inventoryFound);
        Container fromChild = new Container();
        from.addChild(fromChild);
        Inventory inventory = new Inventory(fromChild, 100, wantedProduct1,"dummy");
        inventoryFound.add(inventory);
        supplyChainService.addSupplyLink(wantedProduct1, from, to);
        supplyChainService.addSupplyLink(wantedProduct2, from, to);
        Request request = new Request.Builder(demandName)
     		 .destination(to)
     		 .build();
        
        subject.allocateInventoryTo(request, requiredItem);
        Stream<Allocation> allocations = subject.getAllocations(request);
        long count = subject.getAllocations(request).count();

        assertEquals(1,count);
        allocations.forEach(a -> {
           assertEquals(a.getQuantity(), wantedQuantity);
           assertEquals(fromChild, a.getInventory().getContainer());
        });
    
    }
    
    @Test
    public  void  allocateMultiInventory(){
        String  demandName = "d1";
        final int wantedQuantity = 10;
        Product wantedProduct1 = new Product();
        wantedProduct1.setDescription("widget-alpha");
        wantedProduct1.setReference("widget-a");
        Product wantedProduct2 = new Product();
        wantedProduct2.setDescription("widget-beta");
        wantedProduct2.setReference("widget-b"); 
        List<Inventory> inventoryFound = new ArrayList<Inventory>();
        when(repository.findByContainer(from)).thenReturn(inventoryFound);
        Container fromChild = new Container();
        from.addChild(fromChild);
        Inventory inventory = new Inventory(fromChild, 5, wantedProduct1,"dummy");
        Inventory inventory2 = new Inventory(fromChild, 5, wantedProduct1,"dummy2");
        inventoryFound.add(inventory);
        inventoryFound.add(inventory2);
        supplyChainService.addSupplyLink(wantedProduct1, from, to);
        supplyChainService.addSupplyLink(wantedProduct2, from, to);
        Request request = new Request.Builder(demandName)
        	.destination(to)
        	.build();
        BOMItem requiredItem =  new BOMItem(wantedProduct1,wantedQuantity);
            
		subject.allocateInventoryTo(request, requiredItem);
		Stream<Allocation> allocations = subject.getAllocations(request);
		long count = subject.getAllocations(request).count();

		assertEquals(2,count);
        allocations.forEach(a -> {
           assertEquals(5,a.getQuantity() );
           assertEquals(fromChild, a.getInventory().getContainer());
        });
    }

    
    
    @Test
    public  void  allocateMultiInventoryWithMoreAvailable(){
        String  demandName = "d1";
        final int wantedQuantity = 10;
        final int inventoryQuantity = 5;
        
        Product wantedProduct1 = new Product();
        wantedProduct1.setDescription("widget-alpha");
        wantedProduct1.setReference("widget-a");
        Product wantedProduct2 = new Product();
        wantedProduct2.setDescription("widget-beta");
        wantedProduct2.setReference("widget-b"); 
        List<Inventory> inventoryFound = new ArrayList<Inventory>();
        when(repository.findByContainer(from)).thenReturn(inventoryFound);
        Container fromChild = new Container();
        from.addChild(fromChild);
        Inventory inventory = new Inventory(fromChild, inventoryQuantity, wantedProduct1,"dummy");
        Inventory inventory2 = new Inventory(fromChild, inventoryQuantity, wantedProduct1,"dummy2");
        Inventory inventory3 = new Inventory(fromChild, inventoryQuantity, wantedProduct1,"dummy3");
        inventoryFound.add(inventory);
        inventoryFound.add(inventory2);
        inventoryFound.add(inventory3);
        supplyChainService.addSupplyLink(wantedProduct1, from, to);
        supplyChainService.addSupplyLink(wantedProduct2, from, to);

        Request request = new Request.Builder(demandName)
        		 .destination(to)
        		 .build();
        BOMItem requiredItem =  new BOMItem(wantedProduct1,wantedQuantity);
        subject.allocateInventoryTo(request, requiredItem);
       
        long itemsWithAllocations = subject.getAllocations(request).count();
        assertEquals(2,itemsWithAllocations);  /// quantity of 5 each
        int totalAllocated = subject.getAllocations(request)
        .mapToInt(a -> a.getQuantity())
        .sum();
        
        assertEquals(totalAllocated,wantedQuantity);
        
        
        
        subject.getAllocations(request).forEach(a -> {
           assertEquals(inventoryQuantity,a.getQuantity() );
        
           System.out.println(a.getInventory().getReference()  +":"+a.getQuantity());
        });
    }

    
    @Test
    public  void  allocateMultiInventoryWithNonExtactAvailable(){
        String  demandName = "d1";
        final int wantedQuantity = 11;
        Product wantedProduct1 = new Product();
        wantedProduct1.setDescription("widget-alpha");
        wantedProduct1.setReference("widget-a");
        Product wantedProduct2 = new Product();
        wantedProduct2.setDescription("widget-beta");
        wantedProduct2.setReference("widget-b"); 
        List<Inventory> inventoryFound = new ArrayList<Inventory>();
        when(repository.findByContainer(from)).thenReturn(inventoryFound);
        Container fromChild = new Container();
        from.addChild(fromChild);
        Inventory inventory = new Inventory(fromChild, 6, wantedProduct1,"dummy");
        Inventory inventory2 = new Inventory(fromChild, 3, wantedProduct1,"dummy2");
        Inventory inventory3 = new Inventory(fromChild, 8, wantedProduct1,"dummy3");
        inventoryFound.add(inventory);
        inventoryFound.add(inventory2);
        inventoryFound.add(inventory3);
        supplyChainService.addSupplyLink(wantedProduct1, from, to);
        supplyChainService.addSupplyLink(wantedProduct2, from, to);
        Request request = new Request.Builder(demandName)
        		 .destination(to)
        		 .build();
        BOMItem requiredItem =  new BOMItem(wantedProduct1,wantedQuantity);
        
        subject.allocateInventoryTo(request, requiredItem);
        Stream<Allocation> allocations = subject.getAllocations(request);
        long count = subject.getAllocations(request).count();
        assertEquals(3,count);
        int totalAllocated = subject.getAllocations(request)
        .mapToInt(a -> a.getQuantity())
        .sum();
        
        assertTrue(totalAllocated == wantedQuantity);
        
        allocations.forEach(a -> {
           assertFalse(0 == a.getQuantity() );
        });
    }

    @Test 
    public  void  allocateMultiInventoryWithNotEnoughAvailable(){
        String  demandName = "d1";
        final int wantedQuantity = 11;
        Product wantedProduct1 = new Product();
        wantedProduct1.setDescription("widget-alpha");
        wantedProduct1.setReference("widget-a");
        Product wantedProduct2 = new Product();
        wantedProduct2.setDescription("widget-beta");
        wantedProduct2.setReference("widget-b"); 
        List<Inventory> inventoryFound = new ArrayList<Inventory>();
        when(repository.findByContainer(from)).thenReturn(inventoryFound);
        Container fromChild = new Container();
        from.addChild(fromChild);
        final String inventory1Reference = "dummy";
        Inventory inventory = new Inventory(fromChild, 6, wantedProduct1,inventory1Reference);
        final String inventory2Reference = "dummy2";
        Inventory inventory2 = new Inventory(fromChild, 3, wantedProduct1,inventory2Reference);
        inventoryFound.add(inventory);
        inventoryFound.add(inventory2);
        supplyChainService.addSupplyLink(wantedProduct1, from, to);
        supplyChainService.addSupplyLink(wantedProduct2, from, to);
        
        Request request = new Request.Builder(demandName)
     		 .destination(to)
     		 .build();

        BOMItem requiredItem =  new BOMItem(wantedProduct1,wantedQuantity);
        

        subject.allocateInventoryTo(request, requiredItem);

        Stream<Allocation> allocations = subject.getAllocations(request);
        long count = subject.getAllocations(request).count();
        assertEquals(2,count);
        int totalAllocated = subject.getAllocations(request)
        .mapToInt(a -> a.getQuantity())
        .sum();
        
        assertTrue(totalAllocated< wantedQuantity);
        
        allocations.forEach(a -> {
            switch (a.getInventory().getReference()){
            case inventory1Reference:
                assertEquals(a.getQuantity(),inventory.getQuantity());
                break;
            case inventory2Reference:
                assertEquals(a.getQuantity(),inventory2.getQuantity());
                break;
            }
           
           System.out.println(a.getInventory().getReference()  +":"+a.getQuantity());
        });
    }

    
    
    
}
