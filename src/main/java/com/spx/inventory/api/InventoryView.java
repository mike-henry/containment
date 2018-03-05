package com.spx.inventory.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class InventoryView {
    private String reference;
    private String containerReference;
    private int quantity = 1; // BigDecimal keep it simple though
    private String productReference;
}
