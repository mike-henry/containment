package com.spx.containment.builders;

import com.spx.containment.api.ContainerView;
import com.spx.containment.model.Container;

public interface SpecficContainerFactory<T extends Container>
{

	String getType();

	Class<T> getContainerClass();

	T createContainerFormView(ContainerView view);

	ContainerView createViewContainerContainer(T container);

}
