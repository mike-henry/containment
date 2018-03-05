package com.spx.dropwizard.extensions;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Dropwizard bundle that starts and initializes a weld CDIContainer.
 */
public class WeldBundle implements Bundle {

  
    public void initialize(final Bootstrap<?> bootstrap) {
        // empty
    }

  
    public void run(final Environment environment) {
      //  environment.getApplicationContext().addEventListener(new BeanManagerResourceBindingListener());
      //  environment.getApplicationContext().addEventListener(new Listener());
    }

}
