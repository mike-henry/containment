package com.spx.containment.chain.api;

import javax.inject.Named;

import com.spx.containment.chain.model.Allocation;
import com.spx.containment.chain.model.BOMItem;
import com.spx.containment.chain.model.Request;

@Named
public class ModelToViewAdaptor {

	public RequestView  toRequestView(Request model) {
		RequestView result = new RequestView();
		result.setDestination(model.getDestination().getReference());
		result.setReference(model.getReference());
		setAllocationViews(model, result);
		setRequiredItemViews(model, result);
		return result;
	}


	private void setAllocationViews(Request model, RequestView result) {
		model.getAllocations()
		.stream()
		.map(this::toAllocationView)
		.forEach(v->result.getAllocations().add(v));
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
	
	
	
	

	
	
	
}
