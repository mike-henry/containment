package com.spx.containment.actions;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;

import com.spx.containment.api.ContainerView;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.containment.services.ModelToViewAdaptor;


public class CreateContainerActionTest {

    private ContainerServices cam;
    private ContainerView[] containerViews;
    private ModelToViewAdaptor modelToViewMapper;
    
    @Before
    public void init(){
        cam = mock(ContainerServices.class);
        modelToViewMapper= mock(ModelToViewAdaptor.class);
        containerViews = new ContainerView[0];
        
    }
    
    @Test
    public  void TestCreatContainerAction(){
        Container  reference = new Container();
        
        when(modelToViewMapper.getContainerModel(any())).thenReturn(Optional.of(reference));
        CreateContainerTreeAction action  = new CreateContainerTreeAction(modelToViewMapper, cam, containerViews);   
        action.call();
        verify(cam,times(1)).saveTree(reference);
    }
    
    
}
