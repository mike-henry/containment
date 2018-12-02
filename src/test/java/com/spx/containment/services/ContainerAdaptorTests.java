package com.spx.containment.services;



import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.spx.containment.api.ContainerView;
import com.spx.containment.builders.GeneralContainerViewFactory;
import com.spx.containment.model.Box;
import com.spx.containment.model.Container;
import com.spx.containment.model.Global;

public class ContainerAdaptorTests {

    private ModelToViewAdaptor subject;
    private ContainerServices containerAccessManager;
    private GeneralContainerViewFactory containerFactory;

    @Before
    public void init() {
        containerAccessManager = mock(ContainerServices.class);
        containerFactory = mock(GeneralContainerViewFactory.class);
        subject = new ModelToViewAdaptor(containerFactory, containerAccessManager);
    }

    @Test
    public void getViewArrayTest() {
        String containerName = "container";
        Container container = new Box();
        container.setName(containerName);
        when(containerAccessManager.fetchContainerByName("container")).thenReturn(Optional.of(container));
        ContainerView view = new ContainerView();
        when(containerFactory.createView(container)).thenReturn(view);
        ContainerView[] result = subject.getViewArray(containerName);
        assertEquals(result[0],view);
        System.out.println("");

    }
    @Test
    public void getViewArrayWithCildrenTest() {
        String containerName = "container";
        Container container = new Box();
        Container child = new Box();
        child.setName("child");
        container.setName(containerName);
        container.addChild(child);
        when(containerAccessManager.fetchContainerByName("container")).thenReturn(Optional.of(container));
        ContainerView view = new ContainerView();
        when(containerFactory.createView(container)).thenReturn(view);
        when(containerFactory.createView(child)).thenReturn(view);
        ContainerView[] result = subject.getViewArray(containerName);
        assertEquals(result[0],view);
        assertEquals(result[1],view);
        assertEquals(result.length,2);
    }
    
    @Test
    public void getContainerByName(){
        Container container = new Box();
        container.setReference("reference");
        when(containerAccessManager.findByReference("container")).thenReturn(container);
        Container result = subject.getContainerByReference("container");
        assertEquals(result,container);
        verify(containerAccessManager,times(1)).findByReference("container");
        
        
    }

    @Test 
    public  void  attatchChildTest(){
        Map<String,Container> containers = new HashMap<String,Container>();
        createContainer("parent",containers);
        createContainer("container",containers);
        createContainer("child1",containers);
        createContainer("child2",containers);
        createContainer("child3",containers);
        createContainer("child4",containers);
        ContainerView view = new ContainerView();
        view.setName("container");
        view.setParent("parent");
        view.addChild("child1");
        view.addChild("child2");
        view.addChild("child3");
        view.addChild("child4");
       
        when(containerAccessManager.fetchContainerByName("container")).thenReturn(Optional.of(containers.get("container")));
        when(containerAccessManager.fetchContainerByName("parent")).thenReturn(Optional.of(containers.get("parent")));
        subject.attachChildrentoContainer(containers, view);
        assertEquals(4,containers.get("container").getChildren().size());
        assertEquals(containers.get("container"),containers.get("child1").getParent().orElse( null));
        assertEquals(containers.get("container"),containers.get("child2").getParent().orElse( null));
        assertEquals(containers.get("container"),containers.get("child3").getParent().orElse( null));
        assertEquals(containers.get("container"),containers.get("child4").getParent().orElse( null));
        
        
        
    }
    
    
    
    private void createContainer(String nameAndRef,Map<String,Container> containers){
        Container container = new Container();
        container.setName(nameAndRef);
        container.setReference(nameAndRef);
        containers.put(nameAndRef, container);
    }
    
    
    @Test
    public void  testGetContainerModel(){
        ContainerView[] containerViews = Arrays.array(createView("container","parent","child1","child2","child3","child4"),
        createView("parent","global","container"),
        createView("child1","container"),
        createView("child2","container"),
        createView("child3","container"),
        createView("child4","container")
        );
        
        Map<String,Container> containers = new HashMap<String,Container>();
        containers.put(Global.NAME, new Global());
        
        Answer<Container> createLooseContainerMock = new Answer<Container>(){

            @Override
            public Container answer(InvocationOnMock invocation) throws Throwable {
                ContainerView view =(ContainerView) invocation.getArguments()[0];
                Container container = new Container();
                container.setName(view.getName());
                container.setReference(view.getReference());
                containers.put(container.getName(), container);
                return container;
            }};
            Answer<Optional<Container>> fetchContainerByNameMock = new Answer<Optional<Container>>(){
                
                @Override
                public Optional<Container> answer(InvocationOnMock invocation) throws Throwable {
                    String name =(String) invocation.getArguments()[0];
                    System.out.println(name);
                    return Optional.of(containers.get(name));
                }};
            
        when(containerFactory.createLooseContainer(any(ContainerView.class))).thenAnswer(createLooseContainerMock);
        when(containerAccessManager.fetchContainerByName(any(String.class))).thenAnswer(fetchContainerByNameMock);
        when(containerAccessManager.getGlobal()).thenReturn((Global)containers.get("global"));        
        Optional<Container> topOfTreeContainer = subject.getContainerModel(containerViews);
        assertEquals(topOfTreeContainer.get().getName(),"parent");
    }
    
    
    private ContainerView createView(String name,String parent,String ...childrenNames){
        ContainerView result= new ContainerView();
        result.setName(name);
        result.setParent(parent);
        for (String childName :childrenNames){
            result.addChild(childName);
        }
        return result;
    }
        
}
