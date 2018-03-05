package com.spx.containment.actions;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.api.ContainerView;
import com.spx.containment.services.ContainerServices;
import com.spx.containment.services.ModelToViewAdaptor;

public class ActionTest {

    ActionExecutor subject = new ActionExecutor();

    private ContainerServices services;

    private ModelToViewAdaptor adaptor;
    
    private ContainerActionFactory factory;
    
    
    private Supplier<String>  func= () -> "test-string";
    
    private Callable<String> callable = new Callable<String>(){

        @Override
        public String call() throws Exception {
            return "test-call";
        }
        
    };
    
    
    @Before 
    public void init(){
        services = mock(ContainerServices.class);
        adaptor = mock(ModelToViewAdaptor.class);
        factory = new ContainerActionFactory(adaptor, services);
        
    }
    
    

    @Test
    public void testExecuteFunction() {
        String result =subject.get(func);
        assertEquals("test-string",result);
    }

    @Test
    public void testExecuteCallable() {
        String result =subject.call(callable);
        assertEquals("test-call",result);
    }

    @Test
    public void getContainer() {
        ContainerView wantedResult[] = new ContainerView[0];
        when(adaptor.getViewArray(any())).thenReturn(wantedResult);
        GetContainerTreeAction action = factory.buildGetContainerTreeAction("test");
        ContainerView[] result = subject.call(action);
        verify(adaptor, times(1)).getViewArray("test");
        assertArrayEquals(result,wantedResult);
    }
}
