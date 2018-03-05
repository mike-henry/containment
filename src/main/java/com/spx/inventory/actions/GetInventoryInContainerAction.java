package com.spx.inventory.actions;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.inventory.api.InventoryView;
import com.spx.inventory.services.InventoryLedger;
import com.spx.inventory.services.InventoryMapper;

public class GetInventoryInContainerAction implements Callable<List<InventoryView>> {

    private final InventoryMapper mapper;
    private final InventoryLedger ledger;
    private final ContainerServices containerServices;
    private final  String containerReference;

    public GetInventoryInContainerAction(InventoryMapper modelToViewMapper, InventoryLedger ledger,ContainerServices containerServices, String containerReference) {
        this.mapper = modelToViewMapper;
        this.ledger = ledger;
        this.containerServices=containerServices;
        this.containerReference=containerReference;
    }

    @Override
    public List<InventoryView> call() {
        Container container = containerServices.findByReference(containerReference);
        return ledger.findInventoryInContainer(container).stream().map(mapper::inventoryModelToView)
                .collect(Collectors.toList());
    }

}
