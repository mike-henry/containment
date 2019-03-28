package com.spx.dropwizard.extensions;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import com.spx.general.config.ApplicationConfiguration;
import com.spx.session.shiro.ShiroBootstrap;


public class ShiroWeldExtension implements Extension {


  final ApplicationConfiguration config;

  final BeanFactory beanFactory;


  public ShiroWeldExtension(ApplicationConfiguration config, BeanFactory beanFactory) {
    this.config = config;
    this.beanFactory = beanFactory;
  }


  void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
    ShiroBootstrap shiroBootstrap = new ShiroBootstrap(bm);
    abd.addBean(beanFactory.createBean(bm, shiroBootstrap));
  }


}
