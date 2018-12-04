package com.spx.dropwizard.extensions;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import com.spx.general.ApplicationConfiguration;


public class ConfigWeldExtension implements Extension {

	
	final ApplicationConfiguration config;
	
    final BeanFactory beanFactory;

   

    public ConfigWeldExtension(ApplicationConfiguration config,BeanFactory beanFactory){
        this.config=config;
        this.beanFactory=beanFactory;
    }
    
    


    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
        abd.addBean( beanFactory.createBean(bm,config) );
    }


   
	


    

    
}
