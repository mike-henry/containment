package com.spx.containment.actions;

import java.util.concurrent.Callable;
import com.spx.containment.business.exceptions.NotFoundException;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;

public class RemoveContainerAction implements Callable<Void> {

  private final ContainerServices containerServices;
  private final String containerName;

  public RemoveContainerAction(ContainerServices containerServices, String containerName) {
    this.containerServices = containerServices;
    this.containerName = containerName;
  }

  @Override
  public Void call() {
    Container container =
        containerServices.fetchContainerByName(containerName).orElseThrow(() -> new NotFoundException(containerName));
    containerServices.remove(container);
    return null;
  }
}
