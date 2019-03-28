package com.spx.containment.chain.actions;

import java.util.concurrent.Callable;
import com.spx.containment.chain.api.InventoryRequestService;
import com.spx.containment.chain.api.ModelToApiViewAdaptor;
import com.spx.containment.chain.api.RequestView;
import com.spx.containment.chain.model.Request;

public class CreateInventoryRequestAction  implements  Callable<Void> {
	
	private final InventoryRequestService inventoryRequestService;
	private final ModelToApiViewAdaptor mapper;
	private final RequestView view;
	
	public CreateInventoryRequestAction(InventoryRequestService inventoryRequestService, ModelToApiViewAdaptor mapper,RequestView view) {
		this.inventoryRequestService=inventoryRequestService;
		this.mapper=mapper;
		this.view=view;
	}
	

	@Override
	public Void call() throws Exception {
		Request request = mapper.toModel(view);
		
		//TODO --persist
		return null;
	}

}
