package com.spx.containment.model;

import javax.persistence.Entity;
import com.spx.containment.persistance.ContainmentAccess;

@Entity
@ContainmentAccess
public class Global extends Container
{
	
	public static final String NAME = "global";

	
//	public Global(){
//		this.setParent(this);
//	}
	
	
	

	
}

