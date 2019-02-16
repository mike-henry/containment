package com.spx.general;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.spi.ExternalRequestContext;
import org.glassfish.jersey.server.spi.ExternalRequestScope;

public class RequestScopeBridge<T>  {

	
	public ExternalRequestContext<T> open(ServiceLocator locator) {
		System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXX");
		return null;
	}

	
	public void suspend(ExternalRequestContext<T> c, ServiceLocator locator) {
		System.err.println("YYYYYYYYYYYYYYYYYYYYYY");
		
	}

	
	public void resume(ExternalRequestContext<T> c, ServiceLocator locator) {
		System.err.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		
	}

	
	public void close() {
		// TODO Auto-generated method stub
		System.err.println("???????????????????????????????????????????");
	}

}
