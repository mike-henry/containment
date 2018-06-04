package com.spx.containment.chain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.chain.api.AllocationView;
import com.spx.containment.chain.api.BOMItemView;
import com.spx.containment.chain.api.ModelToViewAdaptor;
import com.spx.containment.chain.api.RequestView;
import com.spx.containment.chain.model.Allocation;
import com.spx.containment.chain.model.BOMItem;
import com.spx.containment.chain.model.Request;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.repository.InventoryRepository;
import com.spx.inventory.services.InventoryLedger;
import com.spx.product.model.Product;

public class ViewConversionTest {

	
	
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
        subject = new InventoryRequestAllocationService(ledger, supplyChainService,null);
    }
	
	@Test 
	public void requestToView() {
		ModelToViewAdaptor adaptor = new ModelToViewAdaptor();
        Request request = createRequest();
        RequestView view =adaptor.toRequestView(request);
        assertEquals(request.getReference(),view.getReference());
        assertEquals(request.getDestination().getReference(),view.getDestination());
        request.getAllocations().stream()
        .forEach(a->checkAllocation(a,view.getAllocations()));
        
        request.getRequiredItems().stream()
        .forEach(a->checkRequiredItems(a,view.getRequiredItems()));
        
	}

	private void checkRequiredItems(BOMItem a, List<BOMItemView> requiredItems) {
		BOMItemView bomtemView=getViewByProductReference(requiredItems,a.getProduct().getReference())
				.orElse(null);
		 assertEquals(bomtemView.getProductReference(),a.getProduct().getReference());
		 assertEquals(bomtemView.getQuantity(),a.getQuantity());
	}

	private Optional<BOMItemView> getViewByProductReference(List<BOMItemView> views, String reference) {
		return views.
		  stream()
		  .filter(view -> view.getProductReference().equals(reference))
		  .findFirst();
	}

	private void checkAllocation(Allocation allocation,List<AllocationView> allocationViews) {
		AllocationView allocationView=getViewByInventoryReference(allocationViews,allocation.getInventory().getReference())
		.orElse(null);
		 assertEquals(allocationView.getInventoryReference(),allocation.getInventory().getReference());
		 assertEquals(allocationView.getQuantity(),allocation.getQuantity());
		 assertEquals(allocationView.getRequestReference(),allocation.getRequest().getReference());
	}
	
	private Optional<AllocationView> getViewByInventoryReference(List<AllocationView> views, String reference) {
		return views.
		  stream()
		  .filter(view -> view.getInventoryReference().equals(reference))
		  .findFirst();
	}
	

	private Request createRequest() {
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
       		 .requiredItem(wantedProduct1,wantedQuantity)
       		 .build();
        subject.allocateTo(request);
		return request;
	}
}
