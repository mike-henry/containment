package com.spx.product.actions;

import static org.mockito.Matchers.any;
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



public class CreateProductActionTest {

    
    private ProductModelToViewMapper mapper;
    private ProductManager productManager;

    @Before
    public void init(){
        mapper =new ProductModelToViewMapper();
        productManager = mock(ProductManager.class);       
    }
    
    
    @Test
    public void  createProduct() throws Exception{
        ProductView[] view = {new ProductView()};
        CreateProductAction subject = new CreateProductAction(productManager, mapper, view);
        subject.call();
        verify(productManager,times(1)).create(any());      
    }
 
    @Test
    public void  doNotcreateProductWithnoView() throws Exception{
        ProductView[] view = {};
        CreateProductAction subject = new CreateProductAction(productManager, mapper, view);
        subject.call();
        verify(productManager,times(0)).create(any());      
    }
    
    @Test
    public void  verifyCreatedWithMappedProduct() throws Exception{
        ProductView[] views = { new ProductView()};
        Product product = new Product();
        mapper=mock(ProductModelToViewMapper.class);
        when(mapper.createModelFromView(any())).thenReturn(product);
        CreateProductAction subject = new CreateProductAction(productManager, mapper, views);
        subject.call();
        verify(productManager,times(1)).create(product);      
    }
    
}
