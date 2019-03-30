package com.spx.containment.actions;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.business.exceptions.NotFoundException;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;

public class RemoveContainerActionTest {

    private ContainerServices cam;

    @Before
    public void init() {
        cam = mock(ContainerServices.class);
    }

    @Test
    public void extistantTestCreatContainerAction() {
        Container reference = new Container();
        when(cam.fetchContainerByName(any())).thenReturn(Optional.of(reference));
        RemoveContainerAction action = new RemoveContainerAction(cam, "reference");
        action.call();
        verify(cam, times(1)).fetchContainerByName("reference");
        verify(cam, times(1)).remove(reference);

    }
    
    @Test(expected=NotFoundException.class)
    public void nonExistantTestCreatContainerAction() {
        when(cam.fetchContainerByName(any())).thenReturn(Optional.ofNullable(null));
        RemoveContainerAction action = new RemoveContainerAction(cam, "reference");
        action.call();
        verify(cam, times(1)).fetchContainerByName("reference");
        verify(cam, times(0)).remove(any());

    }
    
    
}
