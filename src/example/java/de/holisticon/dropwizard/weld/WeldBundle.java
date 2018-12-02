package de.holisticon.dropwizard.weld;

import org.jboss.weld.environment.servlet.BeanManagerResourceBindingListener;
import org.jboss.weld.environment.servlet.Listener;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Dropwizard bundle that starts and initializes a weld CDIContainer.
 */
@Slf4j
public class WeldBundle implements Bundle {

    @Override
    public void initialize(final Bootstrap<?> bootstrap) {
        // empty
    }

    @Override
    public void run(final Environment environment) {
    	
   //    environment.getApplicationContext().addEventListener(new BeanManagerResourceBindingListener());
   //    environment.getApplicationContext().addEventListener(new Listener());
    }

}
