package com.spx.containment.chain.api;

import javax.inject.Named;

import com.spx.containment.chain.model.Allocation;
import com.spx.containment.chain.model.Request;

@Named
public class ModelToViewAdaptor {

	public RequestView  toRequestView(Request model) {
		RequestView result = new RequestView();
		result.setDestination(model.getDestination().getReference());
		result.setReference(model.getReference());
		model.getAllocations()
		.stream()
		.map(this::toAllocationView)
		.forEach(v->result.getAllocations().add(v));
		return result;
	}
	
	
	private AllocationView toAllocationView(Allocation model) {
		AllocationView result = new AllocationView();
		result.setInventoryReference(model.getInventory().getReference());
		result.setQuantity(model.getQuantity());
		result.setRequestReference(model.getRequest().getReference());
		return result;
	}
	
	
	
	
	
	

	
	
	
}
