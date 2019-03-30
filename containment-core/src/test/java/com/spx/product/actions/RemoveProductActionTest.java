package com.spx.product.actions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.spx.product.model.Product;
import com.spx.product.services.ProductManager;

public class RemoveProductActionTest {
   
    private ProductManager productManager;

    @Before
    public void init(){
        productManager = mock(ProductManager.class);       
    }
    
    @Test
    public void actionReturnsProductFromManager() throws Exception{
        String reference = "dummy";
        Product product = new Product();
        product.setReference("ref");
        product.setDescription("the lazy dog jumped... yada yada");
        RemoveProductAction subject = new RemoveProductAction(reference,productManager );
        subject.call();
        verify(productManager, times(1)).removeProductByReference(reference);
    }

}
