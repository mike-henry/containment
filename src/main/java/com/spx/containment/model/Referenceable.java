package com.spx.containment.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@MappedSuperclass
@EqualsAndHashCode
public class Referenceable {

    @Id
    protected UUID id;
    @Column(unique = true)
    protected String name;
    @Column(unique = true)
    protected String reference;

}
