package com.spx.product.api;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.actions.ActionExecutor;
import com.spx.product.actions.CreateProductAction;
import com.spx.product.actions.GetProductAction;
import com.spx.product.actions.ProductActionFactory;
import com.spx.product.actions.RemoveProductAction;

public class ProductResourceTest {
    
    private ActionExecutor executor;
    private ProductActionFactory factory;
    private ProductResource subject;
    @Before
    public void init(){
        executor = mock(ActionExecutor.class);
        factory  = //mock(ProductActionFactory.class);
                  new ProductActionFactory(null,null);
        subject = new ProductResource(factory, executor);
    }
    
    
    @Test
    public void createProduct(){
        ProductView[] views = new ProductView[0];
        subject.createTree(views);
        verify(executor,times(1)).call(any(CreateProductAction.class));        
    }
    
    @Test
    public void getProduct(){
        String reference = "irrelavent";
        subject.getPoduct(reference);
        verify(executor,times(1)).call(any(GetProductAction.class));        
    }
    @Test
    public void removeProduct(){
        String reference = "irrelavent";
        subject.removeProduct(reference);
        verify(executor,times(1)).call(any(RemoveProductAction.class));        
    }
    
}
