package com.spx.session;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartToken
{

	@JsonProperty("token")
	private String value = UUID.randomUUID().toString();

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public boolean equals(Object other)
	{
		return this.value.equals(other.toString());
	}

	@Override
	public int hashCode()
	{
		return this.value.hashCode();
	}

}
