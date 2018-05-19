package com.spx.containment.request;

import com.spx.product.model.Product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class BOMItem {

    private Product product;
    private int quantity;
    
}
