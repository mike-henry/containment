package com.spx.containment.actions;

import javax.inject.Inject;
import javax.inject.Named;

import com.spx.containment.api.ContainerView;
import com.spx.containment.services.ContainerServices;
import com.spx.containment.services.ModelToViewAdaptor;

@Named
public class ContainerActionFactory {

    private final ModelToViewAdaptor mapper;
    private final ContainerServices containerServices;

    @Inject
    public ContainerActionFactory(ModelToViewAdaptor modelToViewMapper,ContainerServices cam) {
        this.mapper=modelToViewMapper;  
        this.containerServices=cam;
    } 
    
    public ContainerActionFactory() {
    	this(null,null);
    } 
    
    public CreateContainerTreeAction buildCreateContainerTreeAction(ContainerView[] views){
        return new CreateContainerTreeAction(mapper, containerServices, views);
    }

   
    public GetContainerTreeAction buildGetContainerTreeAction(String rootName) {
        return new GetContainerTreeAction(mapper,rootName);
    }
    
    public RemoveContainerAction buildRemoveContainerTreeAction(String containerName) {
        return new RemoveContainerAction(containerServices, containerName);
    }
    
}
