package com.spx.containment.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.spx.containment.api.ContainerView;
import com.spx.containment.builders.GeneralContainerViewFactory;
import com.spx.containment.model.Box;
import com.spx.containment.model.Container;
import com.spx.containment.model.Global;
import com.spx.containment.services.ContainerServices;
import com.spx.containment.services.ModelToViewAdaptor;

public class ContainerModelToViewMapperTest {

    private GeneralContainerViewFactory factory = mock(GeneralContainerViewFactory.class);
    ModelToViewAdaptor subject;
    private ContainerServices cam;

    @Before
    public void init() {
        factory = mock(GeneralContainerViewFactory.class);
        cam = mock(ContainerServices.class);
        subject = new ModelToViewAdaptor(factory, cam);
    }

    @Test
    public void TestMappFromView() {
        Container a = createTestContainer("one");
        Container b = createTestContainer("two");
        when(factory.createLooseContainer(any())).thenReturn(a).thenReturn(b);

        ContainerView v[] = new ContainerView[2];

        ContainerView one = new ContainerView();
        one.setName("one");

        ContainerView two = new ContainerView();
        two.setName("two");
        v[0] = one;
        v[1] = two;

      //  Map<String, Container> map = subject.getviewMap(v);

    }

    @Test
    public void getTopContainer() {

        ContainerView child1 = new ContainerView();
        child1.setName("child1");
        ContainerView child2 = new ContainerView();
        child2.setName("child2");
        ContainerView parent = new ContainerView();
        parent.setName("parent");
        Set<String> children = new HashSet<String>();
        children.add("child1");
        children.add("child2");
        parent.setChildren(children);
        ContainerView views[] = { child1, child2, parent };

        Container a = createTestContainer("child1");
        Container b = createTestContainer("child2");
        Container c = createTestContainer("parent");
        when(factory.createLooseContainer(any())).thenReturn(a).thenReturn(b).thenReturn(c);
        when(cam.fetchContainerByName(null)).thenReturn(Optional.of(new Global()));

     //   Map<String, Container> containers = subject.getviewMap(views);
      //  subject.attachChildrentoContainers(containers, views);
        // Container top =
        // subject.findTopTree(containers.values()).orElseThrow(()-> new
        // RuntimeException());

        // assertEquals("parent",top.getName());
        // assertTrue(top.getChildren().contains(a));
    }
    
    @Test
    public void getViewArrayTest(){
        
        Container root = new Box();
        Container child1 = new Box();
        Container child2 = new Box();
        root.addChild(child1);
        root.addChild(child2);
        root.setName("root");
        child1.setName("child1");
        child2.setName("child2");
        when(cam.fetchContainerByName("root")).thenReturn(Optional.of(root));
        
        ContainerView[] result = subject.getViewArray("root");
       
    }

    private Container createTestContainer(String name) {
        Container result = new Box();
        result.setName(name);
        return result;
    }
}
