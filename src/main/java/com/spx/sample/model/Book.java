package com.spx.sample.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="books")
public class Book extends ModelObject {

    
    private String author;
    
    private String title;

}
