package com.spx.containment.chain.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.spx.containment.model.Referenceable;
import com.spx.inventory.model.Inventory;

import lombok.Getter;

@Getter
@Entity
public class Allocation {
	
	@Id
	protected UUID id;
    private Referenceable to;
    private int quantity;
    private Inventory inventory;
    private Request request;

	Allocation(){

	}

    
    private Allocation(Referenceable to, int quantity, Inventory inventory, Request request) {
		this.to = to;
		this.quantity = quantity;
		this.inventory = inventory;
		this.request = request;
	}


	public static class Builder {
        private int quantity;
        private Inventory inventory;
        private Request request;
        
        
        public Builder quantity(int quantity) {
        	this.quantity=quantity;
        	return this;
        }
        
        public Builder request( Request request) {
        	this.request=request;
        	return this;
        }
        
        public Builder inventory( Inventory inventory) {
        	this.inventory=inventory;
        	return this;
        }
        
        public Allocation build() {
          Allocation result = new Allocation(request.getDestination(),  quantity,  inventory,  request);
        
          return result;
        }
        
    }
    
}