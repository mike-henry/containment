package com.spx.containment.actions;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.actions.ActionExecutor;
import com.spx.containment.actions.ContainerActionFactory;
import com.spx.containment.actions.CreateContainerTreeAction;
import com.spx.containment.actions.GetContainerTreeAction;
import com.spx.containment.actions.RemoveContainerAction;
import com.spx.containment.api.ContainerView;
import com.spx.containment.api.ContainmentResource;

public class ContainterResourceTest {
    
    private ActionExecutor executor;
    private ContainerActionFactory factory;
    ContainmentResource subject;
    @Before
    public void init(){
        executor = mock(ActionExecutor.class);
        factory  = mock(ContainerActionFactory.class);
        subject = new ContainmentResource(factory, executor);
    }
    
    
    @Test
    public void createContainer(){
        ContainerView[] views = new ContainerView[0];
        CreateContainerTreeAction action = new CreateContainerTreeAction(null, null, null);
        when(factory.buildCreateContainerTreeAction(views)).thenReturn(action);
        subject.createTree( new ContainerView[0]);
        verify(executor,times(1)).call(action);        
    }
    
    @Test
    public void removeContainer(){
        String containerName = "nuthing";
        RemoveContainerAction action = new RemoveContainerAction(null, null);
        when(factory.buildRemoveContainerTreeAction(containerName)).thenReturn(action);
        subject.removeContainer( containerName);
        verify(executor,times(1)).call(action);        
    }
    
    @Test
    public void getContainer(){
        String containerName = "nuthing";
        GetContainerTreeAction action = new GetContainerTreeAction(null, null);
        when(factory.buildGetContainerTreeAction(containerName)).thenReturn(action);
        subject.getTree( containerName);
        verify(executor,times(1)).call(action);        
    }
    
}
