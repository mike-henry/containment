package com.spx.general.jerseybridge;

import javax.inject.Singleton;

import org.apache.webbeans.config.WebBeansContext;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.jvnet.hk2.internal.DefaultClassAnalyzer;
import org.jvnet.hk2.internal.DynamicConfigurationImpl;
import org.jvnet.hk2.internal.DynamicConfigurationServiceImpl;
import org.jvnet.hk2.internal.InstantiationServiceImpl;
import org.jvnet.hk2.internal.ServiceLocatorRuntimeImpl;
import org.jvnet.hk2.internal.Utilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebBeanServiceLocatorGeneratorImpl  extends org.jvnet.hk2.external.generator.ServiceLocatorGeneratorImpl {

	@Override
	public ServiceLocator create(String name, ServiceLocator parent) {
		log.info("creating H2 to openwebean Brige");
		WebBeanServiceLocator sli = new WebBeanServiceLocator(name,super.create(name, parent),WebBeansContext.currentInstance());
	    DynamicConfigurationImpl dci = new DynamicConfigurationImpl(sli);
        // The service locator itself
        dci.bind(Utilities.getLocatorDescriptor(sli));
        // The injection resolver for three thirty
        dci.addActiveDescriptor(Utilities.getThreeThirtyDescriptor(sli));
        // The dynamic configuration utility
        dci.bind(BuilderHelper.link(DynamicConfigurationServiceImpl.class, false).
                to(DynamicConfigurationService.class).
                in(Singleton.class.getName()).
                localOnly().
                build());
        dci.bind(BuilderHelper.createConstantDescriptor( new DefaultClassAnalyzer(sli)));
        dci.bind(BuilderHelper.createDescriptorFromClass(ServiceLocatorRuntimeImpl.class));
        dci.bind(BuilderHelper.createConstantDescriptor( new InstantiationServiceImpl()));
        dci.commit();
		return sli;
	}
	
	
	
	
}
