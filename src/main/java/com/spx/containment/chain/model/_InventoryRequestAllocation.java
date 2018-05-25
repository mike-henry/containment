package com.spx.containment.chain.model;

import com.spx.inventory.model.Inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Deprecated
public class _InventoryRequestAllocation {

    private _InventoryRequest request;
    private Inventory inventory;
    private int quantity;  
}
