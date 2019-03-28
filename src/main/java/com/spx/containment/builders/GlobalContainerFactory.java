package com.spx.containment.builders;

import javax.inject.Inject;
import com.spx.containment.api.ContainerView;
import com.spx.containment.model.Global;
import com.spx.containment.services.ContainerServices;

public class GlobalContainerFactory implements SpecficContainerFactory<Global>
{

	private Global global;

	@Inject
	GlobalContainerFactory(ContainerServices cam)
	{
		global = cam.getGlobal();
	}

	@Override
	public String getType()
	{

		return "GLOBAL";
	}

	@Override
	public Global createContainerFormView(ContainerView view)
	{
		return global;
	}

	@Override
	public Class<Global> getContainerClass()
	{

		return Global.class;
	}

	@Override
	public ContainerView createViewContainerContainer(Global container)
	{

		return new ContainerView();
	}

}
