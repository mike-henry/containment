package com.spx.inventory.actions;

import javax.inject.Inject;
import javax.inject.Named;

import com.spx.containment.services.ContainerServices;
import com.spx.inventory.api.InventoryView;
import com.spx.inventory.services.InventoryLedger;
import com.spx.inventory.services.InventoryMapper;

@Named
public class InventoryActionFactory {

    private final InventoryLedger ledger;
    private final InventoryMapper inventoryMapper;
    private final ContainerServices containerServices;

    @Inject
    public InventoryActionFactory(InventoryLedger ledger, InventoryMapper inventoryMapper,
            ContainerServices containerServices) {
        this.ledger = ledger;
        this.inventoryMapper = inventoryMapper;
        this.containerServices = containerServices;
    }

    public CreateInventoryAction buildCreateInventoryAction(InventoryView view) {
        return new CreateInventoryAction(inventoryMapper, ledger, view);
    }

    public GetInventoryInContainerAction buildGetInventoryInContainerAction(String containerReference) {
        return new GetInventoryInContainerAction(inventoryMapper, ledger, containerServices, containerReference);
    }

}
