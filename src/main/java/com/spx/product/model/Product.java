package com.spx.product.model;

import javax.persistence.Entity;
import com.spx.containment.model.Referenceable;
import com.spx.containment.persistance.ContainmentAccess;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper=true)
@Entity
@ContainmentAccess
@NoArgsConstructor
public class Product extends Referenceable {

    private String description;
    
    private byte image[];

}
