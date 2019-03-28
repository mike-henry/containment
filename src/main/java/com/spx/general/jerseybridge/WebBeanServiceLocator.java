package com.spx.general.jerseybridge;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.container.BeanManagerImpl;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class WebBeanServiceLocator extends ServiceLocatorImpl {

  private final WebBeansContext webBeansContext;

  public WebBeanServiceLocator(String name, ServiceLocator parent, WebBeansContext webBeansContext) {
    super(name, (ServiceLocatorImpl) parent);
    this.webBeansContext = webBeansContext;
  }

  @Override
  public <T> T getService(Class<T> contractOrImpl, Annotation... qualifiers) throws MultiException {
    log.info("fetching service {}", contractOrImpl.getName());
    MultiException multiException = null;
    T result = null;
    try {
      result = super.getService(contractOrImpl, qualifiers);
    } catch (MultiException error) {
      log.error("while fectching {} ", contractOrImpl.getName());
      multiException = error;
    }

    if (result == null) {
      result = getWebBean(contractOrImpl, qualifiers);
    }
    if (result == null && multiException != null) {
      throw multiException;
    }

    return result;

  }

  @SuppressWarnings("unchecked")
  <T> T getWebBean(Class<T> contractOrImpl, Annotation... qualifiers) {
    log.info("fetching service {} from WebBeans", contractOrImpl.getName());
    BeanManagerImpl bm = webBeansContext.getBeanManagerImpl();

    Set<Bean<?>> beans = bm.getBeans(contractOrImpl, qualifiers);


    CreationalContext<?> creationalContext = bm.createCreationalContext(beans.stream().findFirst().get());
    T object = (T) bm.getReference(beans.stream().findFirst().get(), contractOrImpl, creationalContext);
    return object;
  }
}
