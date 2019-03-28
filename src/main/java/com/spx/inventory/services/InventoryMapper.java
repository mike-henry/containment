package com.spx.inventory.services;

import javax.inject.Inject;
import javax.inject.Named;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.inventory.api.InventoryView;
import com.spx.inventory.model.Inventory;
import com.spx.product.model.Product;
import com.spx.product.services.ProductManager;

@Named
public class InventoryMapper {

    private final ContainerServices containerAdaptor;
    private final ProductManager productManager;

    
    @Inject
    public InventoryMapper(ContainerServices containerAdaptor,ProductManager productManager) {
        this.containerAdaptor = containerAdaptor;
        this.productManager=productManager;  
    }

    public InventoryView inventoryModelToView(Inventory inventory) {
        InventoryView result = new InventoryView();
        result.setContainerReference(inventory.getContainer().getReference());
        result.setProductReference(inventory.getProduct().getReference());
        result.setQuantity(inventory.getQuantity());
        result.setReference(inventory.getReference());
        return result;
    }

    public Inventory buildInventory(InventoryView view) {
        Container container = containerAdaptor.findByReference(view.getContainerReference());
        Product  product =  productManager.findByReference(view.getProductReference());
        return new Inventory(container, view.getQuantity(), product, view.getReference());
    }
}
