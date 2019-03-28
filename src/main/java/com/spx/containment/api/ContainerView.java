package com.spx.containment.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContainerView
{
	@JsonIgnore
	Map<String, Object> additionalProperties = new HashMap<String, Object>();

	private Set<String> children = new HashSet<String>();

	private String parent;

	private String name = "DUMMY";

	private String type;

    private String reference;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Set<String> getChildren()
	{
		return Collections.unmodifiableSet(children);
	}

	public void setChildren(Set<String> k)
	{
		children = k;
	}

	public String getParent()
	{
		return parent;
	}

	public void setParent(String newParent)
	{
		parent = newParent;
	}

	public void addChild(String child)
	{
		children.add(child);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value)
	{
		additionalProperties.put(name, value);
	}

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
       this.reference=reference;
    }

}
