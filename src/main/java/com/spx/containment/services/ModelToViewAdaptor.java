package com.spx.containment.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Named;
import com.spx.containment.api.ContainerView;
import com.spx.containment.builders.GeneralContainerViewFactory;
import com.spx.containment.business.exceptions.NotFoundException;
import com.spx.containment.model.Container;
import com.spx.containment.model.Global;

@Named
public class ModelToViewAdaptor {

  
    
    private ContainerServices  containerAccessManager ;
    
    private GeneralContainerViewFactory   containerFactory;
    
    
    @Inject
    public ModelToViewAdaptor(GeneralContainerViewFactory containerFactory,ContainerServices  containerAccessManager){
        this.containerFactory=containerFactory;
        this.containerAccessManager=containerAccessManager;
    }


    private Map<String, Container> getviewMap(ContainerView[] containerViews){       
        return Stream.of(containerViews)
           .map(containerFactory::createLooseContainer) 
           .collect(Collectors.toMap( container->container.getName(), c -> c));
    }
    
    
    private void attachChildrentoContainers(Map<String,Container> containers,ContainerView[] containerViews ){
        Stream.of(containerViews)
        .forEach((view) ->attachChildrentoContainer(containers, view ));  
    }

    public void attachChildrentoContainer(Map<String,Container> containers,ContainerView view ){
        Container container = containers.get(view.getName());    
        view.getChildren().stream()
           .map(presentContainer ->containers.get(presentContainer))
           .peek(presentContainer ->setParent(view, presentContainer))
           .forEach(presentContainer-> container.addChild(presentContainer) );
    }


    private void setParent(ContainerView view, Container presentContainer) {
        presentContainer.setParent(
            this.containerAccessManager.fetchContainerByName(view.getParent())
            .orElse(this.containerAccessManager.getGlobal())
        );
    }
    
    
    private Optional<Container> findTopTree(Collection<Container> containers){
        
        List<String> names=containers.stream().map(container-> container.getName()).collect(Collectors.toList());
        
        Optional <Container> result = containers.stream()
           .filter(container->  isParentUnknown(names, container))
           .findFirst();      
         if (result.get().getParent().isPresent() == false ){
             result.get().setParent(containerAccessManager.getGlobal());
         }
        
        return result;
    }


    private boolean isParentUnknown(List<String> names, Container container) {
        return !container.getParent().isPresent() ||
         names.contains(container.getParent().get().getName())== false;
    }
    
    public Optional<Container> getContainerModel(ContainerView[] containerViews) {
        Map<String, Container> containers = getviewMap(containerViews);
        attachChildrentoContainers(containers, containerViews);
        Optional<Container> topContainer = findTopTree(containers.values());
        return topContainer;
    }
    

    public ContainerView[] getViewArray(String name){
      return  getViews(name).toArray(new ContainerView[]{});
      
    }

    private Collection<ContainerView> getViews(String name) {
        Container container = getContainerByName(name);
        List<ContainerView> result = new ArrayList<ContainerView>();
        buildViews(result, container);
        return result;
    }


    private Container getContainerByName(String name) {
        Container container = this.containerAccessManager.fetchContainerByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("name not found :%s", name)));
        return container;
    }
    
    public Container getContainerByReference(String reference) {
        return  this.containerAccessManager.findByReference(reference);
    }

    private void buildViews(Collection<ContainerView> result, Container container)   {
        container.getChildren()
         .stream()
         .filter(c ->!c.getClass().equals(Global.class))
        .forEach(child -> buildViews(result, child));
        ContainerView view = containerFactory.createView(container);
        result.add(view);
    }

}
