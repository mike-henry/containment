package com.spx.containment.chain.model;

import java.util.UUID;

import javax.persistence.Id;

import com.spx.product.model.Product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BOMItem {

	@Id
	private UUID id;
    private Product product;
    private int quantity;
    
}
