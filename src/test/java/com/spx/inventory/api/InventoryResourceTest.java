package com.spx.inventory.api;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.actions.ActionExecutor;
import com.spx.inventory.actions.CreateInventoryAction;
import com.spx.inventory.actions.GetInventoryInContainerAction;
import com.spx.inventory.actions.InventoryActionFactory;
import com.spx.inventory.api.InventoryResource;
import com.spx.inventory.api.InventoryView;

public class InventoryResourceTest {
    
    private ActionExecutor executor;
    private InventoryActionFactory factory;
    private InventoryResource subject;
    @Before
    public void init(){
        executor = mock(ActionExecutor.class);
        factory  = mock(InventoryActionFactory.class);
        subject = new InventoryResource(factory, executor);
    }
    
    
    @Test
    public void createInventory(){
        InventoryView view = new InventoryView();
        CreateInventoryAction action = new CreateInventoryAction(null, null, view);
        when(factory.buildCreateInventoryAction(view)).thenReturn(action);
        subject.create(view);
        verify(executor,times(1)).call(action);        
    }
    
    @Test
    public void getInventoryInContainer(){
        String containerReference = "irrelavent";
        GetInventoryInContainerAction action = new GetInventoryInContainerAction(null, null, null, containerReference);
        when(factory.buildGetInventoryInContainerAction(containerReference)).thenReturn(action);
        subject.getInventoryInContainer(containerReference);
        verify(executor,times(1)).call(action);        
    }
    
}
