package com.spx.session;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials
{

	@JsonProperty("user")
	private String user;

	@JsonProperty("token")
	private String token;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@JsonProperty("password")
	private String password;

	@Override
	public boolean equals(Object other)
	{
		return (this.user + "@@@" + this.password).equals(other.toString());
	}

	@Override
	public int hashCode()
	{
		return (this.user + "@@@" + this.password).hashCode();
	}

}
