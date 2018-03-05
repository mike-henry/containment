package com.spx.containment.actions;

import java.util.Optional;
import java.util.concurrent.Callable;

import com.spx.containment.api.ContainerView;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.containment.services.ModelToViewAdaptor;

public class CreateContainerTreeAction implements Callable< Void> {

    private final ModelToViewAdaptor mapper;
    private final ContainerServices containerServices;
    private final ContainerView[] containerViews;

    public CreateContainerTreeAction(ModelToViewAdaptor modelToViewMapper, ContainerServices containerServices,ContainerView[] containerViews) {
        this.mapper = modelToViewMapper;
        this.containerServices = containerServices;
        this.containerViews=containerViews;
    }

    @Override
    public Void call() {
        Optional<Container> parentContainer = mapper.getContainerModel(containerViews);
        containerServices.saveTree(parentContainer.get());
        return null;
    }

}
