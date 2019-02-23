package com.spx.dropwizard.extensions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.enterprise.context.spi.Context;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.Extension;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

import com.spx.general.config.ApplicationConfiguration;


public class ConfigExtension implements Extension {

	
	final ApplicationConfiguration config;
	
    final BeanFactory beanFactory;
    
    public ConfigExtension(ApplicationConfiguration config,BeanFactory beanFactory){
        this.config=config;
        this.beanFactory=beanFactory;
     
    }

    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
        abd.addBean( beanFactory.createBean(bm,config) );
    }

    
}
