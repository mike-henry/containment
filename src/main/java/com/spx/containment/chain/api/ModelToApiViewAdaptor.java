package com.spx.containment.chain.api;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;
import com.spx.containment.chain.model.Allocation;
import com.spx.containment.chain.model.BOMItem;
import com.spx.containment.chain.model.Request;
import com.spx.containment.chain.model.Request.Builder;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.product.model.Product;
import com.spx.product.services.ProductManager;

@Named
public class ModelToApiViewAdaptor {
	
	private final ContainerServices  containerAccessManager;
	private final ProductManager  productManager;
	
	@Inject
	public ModelToApiViewAdaptor(ContainerServices  containerAccessManager,ProductManager  productManager){
		this.containerAccessManager=containerAccessManager;
		this.productManager=productManager;
	}

	public RequestView  toRequestView(Request model) {
		RequestView result = new RequestView();
		result.setDestination(model.getDestination().getReference());
		result.setReference(model.getReference());
		setAllocationViews(model, result);
		setRequiredItemViews(model, result);
		return result;
	}

	private void setAllocationViews(Request model, RequestView result) {
//		model.getAllocations()
//		.stream()
//		.map(this::toAllocationView)
//		.forEach(v->result.getAllocations().add(v));
	}
	
	private void setRequiredItemViews(Request model, RequestView result) {
		model.getRequiredItems()
		.stream()
		.map(this::toRequiredItemView)
		.forEach(v->result.getRequiredItems().add(v));
	}
	
	private AllocationView toAllocationView(Allocation model) {
		AllocationView result = new AllocationView();
		result.setInventoryReference(model.getInventory().getReference());
		result.setQuantity(model.getQuantity());
		result.setRequestReference(model.getRequest().getReference());
		return result;
	}
	
	private BOMItemView toRequiredItemView(BOMItem model) {
		BOMItemView result = new BOMItemView();
		result.setProductReference(model.getProduct().getReference());
		result.setQuantity(model.getQuantity());
		return result;
	}

	public Request toModel(RequestView view) {
		Builder builder = new Request.Builder(view.getReference())
				.destination(getDestinationContainer(view));
		for(BOMItemView requiredItemView:view.getRequiredItems()) {
			builder = builder.requiredItem(getRequiredItem(requiredItemView));
		}
		return builder.build();
	}

	private BOMItem getRequiredItem(BOMItemView view) {
		return new BOMItem(UUID.randomUUID(), getProduct(view),view.getQuantity());
	}

	private Product getProduct(BOMItemView view) {
		return productManager.findByReference(view.getProductReference());
	}

	private Container getDestinationContainer(RequestView view) {
		return containerAccessManager.findByReference(view.getDestination());
	}
	
	
}
