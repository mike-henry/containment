package com.spx.containment.actions;

import java.util.concurrent.Callable;
import com.spx.containment.api.ContainerView;
import com.spx.containment.services.ModelToViewAdaptor;

public class GetContainerTreeAction implements Callable<ContainerView[]> {

  private final ModelToViewAdaptor mapper;
  private final String rootName;

  public GetContainerTreeAction(ModelToViewAdaptor modelToViewMapper, String rootName) {
    this.mapper = modelToViewMapper;
    this.rootName = rootName;
  }

  @Override
  public ContainerView[] call() {
    return mapper.getViewArray(rootName);
  }
}
