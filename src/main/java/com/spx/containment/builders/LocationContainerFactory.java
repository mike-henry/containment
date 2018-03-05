package com.spx.containment.builders;

import com.spx.containment.api.ContainerView;
import com.spx.containment.model.Location;

public class LocationContainerFactory implements SpecficContainerFactory<Location>
{

	@Override
	public String getType()
	{

		return "Location";
	}

	@Override
	public Location createContainerFormView(ContainerView view)
	{
		Location result = new Location();

		return result;
	}

	@Override
	public Class<Location> getContainerClass()
	{

		return Location.class;
	}

	@Override
	public ContainerView createViewContainerContainer(Location container)
	{
		ContainerView result = new ContainerView();

		return result;
	}

}
