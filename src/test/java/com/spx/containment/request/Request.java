package com.spx.containment.request;

import java.util.ArrayList;
import java.util.List;

import com.spx.containment.model.Container;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class Request {
    
    private final String reference;
    private Container destination;

    private final List<Allocation> allocations = new ArrayList<Allocation>();
    private final List<BOMItem> requiredItems = new ArrayList<BOMItem>();
    
    public Request(String reference,List<BOMItem> requiredItems,Container destination){
        this.reference=reference;
        this.destination=destination;
        this.requiredItems.addAll(requiredItems);
    }
    
    
    
}
