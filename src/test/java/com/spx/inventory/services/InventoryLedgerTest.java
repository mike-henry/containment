package com.spx.inventory.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.model.Box;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.repository.InventoryRepository;
import com.spx.product.model.Product;

public class InventoryLedgerTest {

    private InventoryLedger subject;
    private InventoryRepository repository = mock(InventoryRepository.class);
    private ContainerServices containerServices= mock(ContainerServices.class);
    
    @Before
    public void init(){
        repository = mock(InventoryRepository.class);
        containerServices= mock(ContainerServices.class);
        subject= new InventoryLedger(repository,containerServices);
    }
    
    @Test
    public void  addToContainer(){
        Inventory inventory = new Inventory();
        Container container = new Box();
        List<Inventory> resultOfFind = new ArrayList<Inventory>();
        when(repository.findByContainer(container)).thenReturn(resultOfFind);
        subject.addToContainer(container, inventory);
        assertTrue(resultOfFind.contains(inventory));
    }
    
    @Test
    public void getContainerContentsOfProductTest(){
        Product product1 = new Product();
        product1.setName("1");
        product1.setDescription("XX");
        Product product2 = new Product();
        product2.setName("2");
        product2.setDescription("YY");
        Container container = new Box();
        Inventory inventory1 = new Inventory(container,1,product1,"ref1"); 
        Inventory inventory2 = new Inventory(container,1,product1,"ref2"); 
        Inventory inventory3 = new Inventory(container,1,product1,"ref3"); 
        Inventory inventory4 = new Inventory(container,1,product2,"ref4"); 
        List<Inventory> resultOfFind = new ArrayList<Inventory>();
        resultOfFind.add(inventory1);
        resultOfFind.add(inventory2);
        resultOfFind.add(inventory3);
        resultOfFind.add(inventory4);
        when(repository.findByContainer(container)).thenReturn(resultOfFind); 
        Set<Container> desendants = new HashSet<Container>();
        desendants.add(container);
        when(containerServices.getDecendants(container)).thenReturn(desendants);
        
        assertEquals(3,subject.getContainerContentsOfProduct(container, product1)
        .size());
        assertEquals(1,subject.getContainerContentsOfProduct(container, product2).size());
    }

    @Test
    public void containerContentsOfProductTest(){
        Product product1 = new Product();
        product1.setName("product1");
        Product product2 = new Product();
        product2.setName("product2");
        Container container = new Box();
        container.setReference("container");

        Container child = new Container();
        child.setReference("child");
        container.addChild(child);
        Inventory inventory1 = new Inventory(container,1,product1,"ref1"); 
        Inventory inventory2 = new Inventory(container,1,product1,"ref2"); 
        Inventory inventory3 = new Inventory(child,1,product2,"ref3"); 
        List<Inventory> resultOfFind = new ArrayList<Inventory>();
        resultOfFind.add(inventory1);
        resultOfFind.add(inventory2);
        List<Inventory> resultOfFind2 = new ArrayList<Inventory>();
        resultOfFind2.add(inventory3);
        
        when(repository.findByContainer(container)).thenReturn(resultOfFind);   
        when(repository.findByContainer(child)).thenReturn(resultOfFind2);   
        
        assertTrue(subject.containerHasProduct( product1, container));
        assertTrue(subject.containerHasProduct( product2, container));
        assertTrue(subject.containerHasProduct( product2, child));
        assertFalse(subject.containerHasProduct( product1, child));
    }
    
    @Test
    public void testSave(){
        Inventory inventory = new Inventory();
        subject.save(inventory);
        verify(repository,times(1)).save(inventory);
    }
    
    @Test
    public void testfindInventoryInContainer(){
        Container container = new Box();
        Container child1 = new Box();
        child1.setReference("child1");
        Container child2 = new Box();
        child2.setReference("child2");
        Set<Container> descedantContainers= new HashSet<Container>();
        descedantContainers.add(child1);
        descedantContainers.add(child2);
        when(containerServices.getDecendants(container)).thenReturn(descedantContainers); 

        Product product1 = new Product();
        List<Inventory> resultOfFind = new ArrayList<Inventory>();
        Inventory inventory1 = new Inventory(container,1,product1,"ref1"); 
        Inventory inventory2 = new Inventory(container,1,product1,"ref2"); 
        resultOfFind.add(inventory1);
        resultOfFind.add(inventory2);
        when(repository.findByContainers(descedantContainers)).thenReturn(resultOfFind);
        
        Collection<Inventory> result = subject.findInventoryInContainer(container);

        verify(containerServices,times(1)).getDecendants(container);
        verify(repository,times(1)).findByContainers(descedantContainers);
        assertTrue(result.containsAll(resultOfFind));
    }

}
