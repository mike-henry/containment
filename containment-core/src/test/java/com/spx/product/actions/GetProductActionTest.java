package com.spx.product.actions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.spx.product.api.ProductView;
import com.spx.product.model.Product;
import com.spx.product.services.ProductManager;
import com.spx.product.services.ProductModelToViewMapper;



public class GetProductActionTest {

    
    private ProductModelToViewMapper mapper;
    private ProductManager productManager;

    @Before
    public void init(){
        mapper =new ProductModelToViewMapper();
        productManager = mock(ProductManager.class);       
    }
    
    
    @Test
    public void actionReturnsProductFromManager() throws Exception{
        String reference = "dummy";
        Product product = new Product();
        product.setReference("ref");
        product.setDescription("the lazy dog jumped... yada yada");
        GetProductAction subject = new GetProductAction(productManager, mapper, reference);
        when(productManager.findByReference(reference)).thenReturn(product);
        ProductView result = subject.call();
        verify(productManager, times(1)).findByReference(reference);
        assertEquals(product.getReference(),result.getReference());
        assertEquals(product.getDescription(),result.getDescription());
    }
    
    
    
    
    
}
