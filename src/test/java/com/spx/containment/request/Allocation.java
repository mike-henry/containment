package com.spx.containment.request;

import com.spx.containment.model.Referenceable;
import com.spx.inventory.model.Inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Allocation {
   
    private Referenceable to;
    private int quantity;
    private Inventory inventory;
    private Request request;
}