package com.spx.inventory.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.spx.containment.model.Container;
import com.spx.containment.model.Referenceable;
import com.spx.containment.persistance.ContainmentAccess;
import com.spx.product.model.Product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper=true)
@Entity
@ContainmentAccess
@NoArgsConstructor
public class Inventory extends Referenceable {

    @ManyToOne
    private Container container;

    @Basic
    private int quantity = 1; // BigDecimal keep it simple though
    
    @ManyToOne
    private Product product;

    public Inventory(Container container, int quantity, Product product, String reference) {
        this.container = container;
        this.quantity = quantity;
        this.product = product;
        this.reference = reference;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
