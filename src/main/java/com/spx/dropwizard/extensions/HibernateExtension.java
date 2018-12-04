package com.spx.dropwizard.extensions;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import com.spx.general.ApplicationConfiguration;


public class HibernateExtension implements Extension {

	
	private final ApplicationConfiguration config;
    private final BeanFactory beanFactory;
	
   

   
@Inject
    public HibernateExtension(ApplicationConfiguration config,BeanFactory beanFactory){
        this.config=config;
        this.beanFactory=beanFactory;
    }
    
    


    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
    //    JPAHibernateBootstrapper jpaBootStrapper = new JPAHibernateBootstrapper(config);
//        abd.addBean( beanFactory.createBean(bm,jpaBootStrapper) );
    }


  


    

    
}
