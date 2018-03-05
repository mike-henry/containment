package com.spx.containment.builders;

import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.spx.containment.api.ContainerView;
import com.spx.containment.model.Container;
import com.spx.containment.model.Referenceable;
import com.spx.containment.services.ContainerServices;

@RequestScoped
public class GeneralContainerViewFactory
{

	@Inject
	@Any
	private Instance<SpecficContainerFactory<? extends Container>> services;

	@Inject
	private ContainerServices cam;

	@Inject
	public GeneralContainerViewFactory(@Any Instance<SpecficContainerFactory<? extends Container>> services, ContainerServices cam)
	{
		super();
		this.services = services;
		this.cam = cam;
	}

	public GeneralContainerViewFactory()
	{
	}

	public Container createLooseContainer(ContainerView view)
	{
		Container container = cam.fetchContainerByName(view.getName())
		        .orElseGet( () ->createFromFactory(view));
		return container;
	}

	private Container createFromFactory(ContainerView view)
	{
		SpecficContainerFactory<? extends Container> factory = getFactory(view);
		Container container = factory.createContainerFormView(view);
		Container parent =cam.fetchContainerByName(view.getParent()).orElseThrow(() -> new RuntimeException("Parent not found")) ;
		container.setName(view.getName());
		container.setParent(parent);
		container.setReference(view.getReference());
		//container =cam.save(container);
		return container;
	}

	private SpecficContainerFactory<? extends Container> getFactory(ContainerView view)
	{
		Optional<SpecficContainerFactory<? extends Container>> result = StreamSupport.stream(services.spliterator(), false)
		        .filter((factory) -> factory.getType().equals(view.getType())).findFirst();
		if (result.isPresent() == false)
		{
			throw new IllegalArgumentException("Unknown Type to constuct " + view.getType());
		}
		return result.get();
	}

	private SpecficContainerFactory<? extends Container> getFactory(Referenceable container)
	{

		Optional<SpecficContainerFactory<? extends Container>> result = StreamSupport.stream(services.spliterator(), false)
		        .filter((factory) -> factory.getContainerClass().isAssignableFrom(container.getClass())).findAny();

		if (result.isPresent() == false)
		{
			throw new IllegalArgumentException("Unknown Type to constuct " + container.getClass());
		}
		return result.get();
	}

	public <T extends Container> ContainerView createView(T container)
	{
		@SuppressWarnings("rawtypes")
		SpecficContainerFactory factory = getFactory(container);
		@SuppressWarnings("unchecked")
		ContainerView view = factory.createViewContainerContainer(container);

		view.setName(container.getName());
		view.setReference(container.getReference());
		view.setParent(container.getParent().orElse(new Container()).getName());
		container.getChildren().forEach(child -> view.addChild(child.getName()));
		view.setType(factory.getType());

		return view;
	}

}
