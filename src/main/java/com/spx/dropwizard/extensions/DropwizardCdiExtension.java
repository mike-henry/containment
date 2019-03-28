package com.spx.dropwizard.extensions;

import static org.slf4j.LoggerFactory.getLogger;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import org.slf4j.Logger;
import com.google.common.collect.Sets;
import com.spx.general.config.ApplicationConfiguration;


class DropwizardCdiExtension implements Extension {

	
	
	
    private final Logger logger = getLogger(this.getClass());

    private final Set<String> names = Sets.newHashSet();

    void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
        logger.error("============================> beginning the scanning process");
    }

    <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
    	
    	
        final String name = pat.getAnnotatedType().getJavaClass().getName();
        logger.debug("============================> scanning type: {}", name);
        names.add(name);
    }

    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
        logger.error("============================> finished the scanning process");
    }
    
    
    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {


        //use this to read annotations of the class

        AnnotatedType<ApplicationConfiguration> at = bm.createAnnotatedType(ApplicationConfiguration.class);


        createBean(abd, bm, at);

    }

	private void createBean(AfterBeanDiscovery abd, BeanManager bm, AnnotatedType<ApplicationConfiguration> at) {
		//use this to instantiate the class and inject dependencies

	}



    public Set<String> getNames() {
        return names;
    }
}
