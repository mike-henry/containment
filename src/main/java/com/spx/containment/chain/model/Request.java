package com.spx.containment.chain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.spx.containment.model.Container;
import com.spx.containment.model.Referenceable;
import com.spx.product.model.Product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper=true)
public class Request extends Referenceable { 
    
 
    @ManyToOne
	private  Container destination;

    
    @OneToMany
    private  List<Allocation> allocations = new ArrayList<Allocation>();
    @OneToMany
    private  List<BOMItem> requiredItems = new ArrayList<BOMItem>();
    
    
    public Request() {
    	
    }
    
    Request(String reference,List<BOMItem> requiredItems,Container destination){
        this.reference=reference;
        this.destination=destination;
        this.requiredItems.addAll(requiredItems);
    }
    
    public static class Builder {
    	 private String reference;
    	 private Container destination;
    	 private List<Allocation> allocations = new ArrayList<Allocation>();
    	 private List<BOMItem> requiredItems = new ArrayList<BOMItem>();
    	 
    	 public Builder (String reference) {
    		 this.reference=reference;
    	 }

      	 public Builder destination(Container destination) {
    		 this.destination=destination;
    		 return this;
    	 }

      	 public Builder allocation(Allocation allocation) {
    		 this.allocations.add(allocation);
    		 return this;
    	 }    	 

      	 public Builder requiredItem(BOMItem requiredItem) {
    		 this.requiredItems.add(requiredItem);
    		 return this;
    	 }
      	 
    	 public Builder requiredItem(Product product,int quatity) {
    		 return requiredItem(new BOMItem(UUID.randomUUID(),product, quatity));
    	 }
      	 
      	 public Request build() {
      		 Request result = new Request( reference,requiredItems, destination);
      		 return result;
      	 }

    }
}
