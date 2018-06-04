
package com.spx.containment.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.spx.containment.business.exceptions.NotFoundException;
import com.spx.containment.model.Box;
import com.spx.containment.model.Container;
import com.spx.containment.model.Global;
import com.spx.containment.model.Location;
import com.spx.containment.persistance.ContainerRepository;

public class ContainerServicesTest {

    private ContainerServices subject;
    private ContainerRepository repository;
    
    
    private Answer<Container> containerSaveAnswer;
    
    
    

    @Before
    public void init() {
        subject = new ContainerServices();
        repository = mock(ContainerRepository.class);
        subject = new ContainerServices(repository);
       
        containerSaveAnswer = new Answer<Container>() {
   		  @Override
			public Container answer(InvocationOnMock invocation) throws Throwable {
				return (Container) invocation.getArguments()[0];
		}};
      
        
    }

    @Test
    public void checkGetGlobal() {
        Optional<Container> global = Optional.of(new Global());
        when(repository.findByName(Global.NAME)).thenReturn(global);
        Global result = subject.getGlobal();
        assertEquals(global.get(), result);
    }
    
    @Test
    public void checkGetGlobalNotFound() {
        Optional<Container> global = Optional.ofNullable(null);
        when(repository.findByName(Global.NAME)).thenReturn(global);
        Global result = subject.getGlobal();
        verify(repository,times(2)).save(any(Global.class)); 
    }
    
    
    @Test
    public void checkFindByReference(){
        Container  container = new Container();
        container.setReference("reference");
        Optional<Container> optionalContainer = Optional.ofNullable(container);
        when(repository.findByReference("reference")).thenReturn(optionalContainer);
        Container result = subject.findByReference("reference");
        verify(repository,times(1)).findByReference("reference");
        assertEquals("reference",result.getReference());
    }
    
    @Test(expected=NotFoundException.class)
    public void checkFindByReferenceNonExist(){
        Optional<Container> optionalContainer = Optional.ofNullable(null);
        when(repository.findByReference("reference")).thenReturn(optionalContainer);
        subject.findByReference("reference");
    }
    
    @Test
    public void  fetchContainerByNameAndClass(){
        Container  container = new Box();
        container.setName("reference");
        Optional<Container> optionalContainer = Optional.ofNullable(container);
        when(repository.findByName("reference")).thenReturn(optionalContainer);
        Optional<Box> result = subject.fetchContainerByName("reference",Box.class);
        verify(repository,times(1)).findByName("reference");
        assertEquals("reference",result.get().getName());
    }
    
    @Test
    public void  fetchContainerByNameAndWrongClass(){
        Container  container = new Box();
        container.setName("reference");
        Optional<Container> optionalContainer = Optional.ofNullable(container);
        when(repository.findByName("reference")).thenReturn(optionalContainer);
        Optional<Location> result = subject.fetchContainerByName("reference",Location.class);
        verify(repository,times(1)).findByName("reference");
        assertFalse(result.isPresent());
    }
    
    @Test
    public void testSaveContainer(){
        Container container = new Container();
        container.setName("name");
        Container parent = new Container();
        parent.setName("parent");
        container.setParent(parent);
        subject.save(container);
        verify(repository,times(1)).save(container);
        verify(repository,times(1)).save(parent);
    }
    
    @Test
    public void testSaveTree(){
        Container container = new Container();
        container.setName("name");
        Container parent = new Container();
        parent.setName("parent");
        when(repository.findByName(Global.NAME)).thenReturn(Optional.ofNullable(null));
        when(repository.save(any(Container.class))).then(containerSaveAnswer);
        container.setParent(parent);
        subject.saveTree(parent);
    }

}
