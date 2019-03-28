package com.spx.containment.builders;

import java.math.BigDecimal;
import com.spx.containment.api.ContainerView;
import com.spx.containment.model.Box;

public class BoxContainerFactory implements SpecficContainerFactory<Box>
{

	public static String HEIGHT = "height";

	public static String WIDTH = "width";

	public static String DEPTH = "depth";

	@Override
	public String getType()
	{
		return "Box";
	}

	@Override
	public Box createContainerFormView(ContainerView view)
	{
		Box result = new Box();
		result.setHeight(getDoubleAdditionalProperty(view, HEIGHT));
		result.setDepth(getDoubleAdditionalProperty(view, DEPTH));
		result.setWidth(getDoubleAdditionalProperty(view, WIDTH));
		return result;
	}

	@Override
	public ContainerView createViewContainerContainer(Box box)
	{
		ContainerView result = new ContainerView();
		result.getAdditionalProperties().put(HEIGHT, box.getHeight().toPlainString());
		result.getAdditionalProperties().put(DEPTH, box.getDepth().toPlainString());
		result.getAdditionalProperties().put(WIDTH, box.getWidth().toPlainString());

		return result;
	}

	private BigDecimal getDoubleAdditionalProperty(ContainerView view, String propertyName)
	{

		return BigDecimal.valueOf(Double.parseDouble((String) view.getAdditionalProperties().getOrDefault(propertyName, "0")));
	}

	@Override
	public Class<Box> getContainerClass()
	{

		return Box.class;
	}

}
