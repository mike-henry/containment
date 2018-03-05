package com.spx.sample.model;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;


@Repository
public interface BookRepository extends EntityRepository<Book, Integer>{
    
    
    Book findByTitle(String title);

}
