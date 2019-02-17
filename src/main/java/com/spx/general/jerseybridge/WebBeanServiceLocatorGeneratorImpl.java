package com.spx.general.jerseybridge;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Singleton;

import org.apache.webbeans.config.WebBeansContext;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.jvnet.hk2.internal.DefaultClassAnalyzer;
import org.jvnet.hk2.internal.DynamicConfigurationImpl;
import org.jvnet.hk2.internal.DynamicConfigurationServiceImpl;
import org.jvnet.hk2.internal.InstantiationServiceImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
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

	
	
	private static  class WebBeanServiceLocator extends ServiceLocatorImpl {
		
		///////////////////// TO BE  CONITNUED!!!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		@Override
		public <T> T getService(Class<T> contractOrImpl, Annotation... qualifiers) throws MultiException {
			log.info("fetching service {}",contractOrImpl.getName());
			MultiException multiException = null;
			T result = null;
			try {
			   result = super.getService(contractOrImpl, qualifiers);
			} catch (MultiException error) {
				multiException=error;
			}
			
			if (result == null){
				result =getWebBean(contractOrImpl, qualifiers);
			}
			if (result ==  null && multiException != null) {
				throw  multiException;
			}
			
			return result;
			
		}
	
		
		@SuppressWarnings("unchecked")
		<T> T getWebBean(Class<T> contractOrImpl, Annotation... qualifiers) {
			log.info("fetching service {} from WebBeans",contractOrImpl.getName());
			BeanManager bm = webBeansContext.getBeanManagerImpl();
			Set<Bean<?>> beans = bm.getBeans(contractOrImpl, qualifiers);
			CreationalContext<?> creationalContext = bm.createCreationalContext(beans.stream().findFirst().get());
			T object = (T) bm.getReference(beans.stream().findFirst().get(), contractOrImpl, creationalContext);
			return object;
		}

		WebBeansContext webBeansContext;

		  public WebBeanServiceLocator(String name, ServiceLocator parent, WebBeansContext webBeansContext) {
		    super(name, (ServiceLocatorImpl) parent);
		    this.webBeansContext=webBeansContext;
		   
		    
		    
		    
		    
		  }
		}
	
	
	
	
}
