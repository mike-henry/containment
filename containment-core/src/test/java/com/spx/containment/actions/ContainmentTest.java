package com.spx.containment.actions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.junit.Test;

import com.spx.containment.model.Box;
import com.spx.containment.model.Container;
import com.spx.containment.persistance.ContainerRepository;
import com.spx.containment.services.ContainerServices;

public class ContainmentTest {

    ContainerRepository repository = mock(ContainerRepository.class);

    ContainerServices subject = new ContainerServices(repository);

    @Test
    public void getDescendantsTest() {
        Container parent = new Box();
        parent.setName("parent");
        
        Container root = new Box();
        root.setName("root");
        Container child1 = new Box();
        child1.setName("child1");
        Container child2 = new Box();
        child2.setName("child2");
        Container grandchild1 = new Box();
        child2.setName("grandChild1");
        parent.addChild(root);
        root.addChild(child1);
        root.addChild(child2);
        child2.addChild(grandchild1);
        Set<Container> result = subject.getDecendants(root);

        assertEquals(true, result.contains(root));
        assertEquals(true, result.contains(child1));
        assertEquals(true, result.contains(child2));
        assertEquals(true, result.contains(grandchild1));
        assertEquals(false, result.contains(parent));
        
    }
    
    

}
