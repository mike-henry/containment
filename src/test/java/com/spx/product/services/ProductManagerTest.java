package com.spx.product.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.business.exceptions.NotFoundException;
import com.spx.product.model.Product;
import com.spx.product.repository.ProductRepository;

public class ProductManagerTest {

    
    private ProductManager subject;
    private ProductRepository repository;
    
    @Before
    public void init(){
        subject = new ProductManager();
        repository = mock(ProductRepository.class);
        subject = new ProductManager(repository);
    }
    
    @Test
    public void createCallsSave(){
      Product product = new Product();
      subject.create(product);
      verify(repository,times(1)).save(product);
    }
    
    @Test
    public void removeCallsRemove(){
        String reference = "ref";
        Product product = new Product();
        when(repository.findByReference(reference)).thenReturn(Optional.of(product));
        subject.removeProductByReference(reference);
        verify(repository,times(1)).remove(product);
    }
    
    @Test
    public void findByReferenceWhenProductExists(){
      String reference = "ref";
      Product product = new Product();
      when(repository.findByReference(reference)).thenReturn(Optional.of(product));
      Product result = subject.findByReference(reference);
      assertEquals(result,product);
      verify(repository,times(1)).findByReference(reference);
    }
    
    @Test(expected=NotFoundException.class)
    public void findbyReferenceWhenProductNotExist(){
        String reference = "ref";
        when(repository.findByReference(reference)).thenReturn(Optional.ofNullable(null));
        subject.findByReference(reference);
    }
    @Test
    public void findByNameWhenProductExists(){
        String name = "name";
        Product product = new Product();
        when(repository.findByName(name)).thenReturn(Optional.of(product));
        Product result = subject.findByName(name);
        assertEquals(result,product);
        verify(repository,times(1)).findByName(name);
    }
    
    @Test(expected=NotFoundException.class)
    public void findbyNameWhenProductNotExist(){
        String name = "name";
        when(repository.findByName(name)).thenReturn(Optional.ofNullable(null));
        subject.findByName(name);
    }
    
    
    
}
