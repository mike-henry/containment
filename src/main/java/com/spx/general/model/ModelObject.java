package com.spx.general.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Version;
import javax.persistence.SequenceGenerator;
import lombok.Data;

@MappedSuperclass
@Data
public class ModelObject {

  private static final int INITIAL_VALUE = 0;
    //  @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "objectSequenceGenerator" )
    @SequenceGenerator(
            name = "objectSequenceGenerator",
            sequenceName = "object_sequence",
            initialValue = INITIAL_VALUE,
            allocationSize = 10)
    public Long getId() {
        return id;
    }
    
    
    

//    @Version
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date version;
}
