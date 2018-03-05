package com.spx.containment.chain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    
    private String  name;
    private String product;
    private int   quantity;

}
