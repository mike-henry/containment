package com.spx.inventory.actions;

import java.util.concurrent.Callable;
import com.spx.inventory.api.InventoryView;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.services.InventoryLedger;
import com.spx.inventory.services.InventoryMapper;

public class CreateInventoryAction implements Callable<Void> {

    private final InventoryMapper mapper;
    private final InventoryLedger ledger;
    private final InventoryView view;

    public CreateInventoryAction(InventoryMapper modelToViewMapper, InventoryLedger ledger, InventoryView view) {
        this.mapper = modelToViewMapper;
        this.ledger = ledger;
        this.view=view;
    }

    @Override
    public Void call() {
        Inventory inventory = mapper.buildInventory(view);
        ledger.save(inventory);
        return null;
    }

}
