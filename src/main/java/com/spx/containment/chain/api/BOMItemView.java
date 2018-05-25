package com.spx.containment.chain.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class BOMItemView {

    private String productReference;
    private int quantity;
    
}
