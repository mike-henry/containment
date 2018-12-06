package com.spx.containment.chain.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.spx.containment.model.Container;
import com.spx.containment.model.Referenceable;
import com.spx.inventory.model.Inventory;

import lombok.Getter;

@Getter

@Entity
public class Allocation {
	
	@Id
	protected UUID id;
	
	@ManyToOne
    private Container to;
    private int quantity;
    
    @ManyToOne
    private Inventory inventory;
    @ManyToOne
    private Request request;

	protected Allocation(){

	}

    
    private Allocation(Container to, int quantity, Inventory inventory, Request request) {
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
          result.id=UUID.randomUUID();
          return result;
        }
        
    }
    
}