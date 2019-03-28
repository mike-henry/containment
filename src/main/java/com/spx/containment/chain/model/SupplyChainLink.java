package com.spx.containment.chain.model;

import com.spx.containment.model.Container;
import com.spx.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplyChainLink {

    private Container from;
    private Container to;
    private Product product;
    
}
