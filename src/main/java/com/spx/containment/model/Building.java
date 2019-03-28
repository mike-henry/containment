package com.spx.containment.model;

import javax.persistence.Entity;
import com.spx.containment.persistance.ContainmentAccess;

@Entity
@ContainmentAccess
public class Building extends Container
{
	private Double latitude;

	private Double longitude;

	private String address1;

	private String address2;

	private String code;

	private String country;

	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String coountry)
	{
		country = coountry;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}
}
