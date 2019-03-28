package com.spx.session.shiro;

import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Any
@Default
public class ShiroBootstrap {

    private static final String REALMS = "realms";
    private static final String REALM_JNDI_NAME = REALMS+"/ApplRealm";
   
    private  BeanManager beanManager;

    public ShiroBootstrap(BeanManager beanManager) {
        this.beanManager = beanManager;
    }
 
    public ShiroBootstrap() {
    }

    @PostConstruct
    public void setup() {
        // constructor injection
        log.info("binding sucurity application realm");
        ApplRealm appRealm = new ApplRealm(beanManager);

        // bind it so shiro can find it!
        bind(REALM_JNDI_NAME, appRealm);
        log.info("Shiro bound to context");
    }

    @PreDestroy
    public void destroy() {
        safeUnbind(REALM_JNDI_NAME); // clean-up!
        log.info("Shiro unbound fromn context");
    }

    private void bind(String name, Object object) {
        try {
            InitialContext ic = getContext();
            if(lookup(ic,REALMS,Object.class).isPresent() == false ){
               ic.createSubcontext(REALMS);
            }
            ic.bind(name, object);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }
    
    
    @SuppressWarnings("unchecked")
    <T> Optional<T>  lookup(Context ctx,String name,Class<T> type){
        Optional<T> result = Optional.ofNullable(null);
        try {
            result=Optional.ofNullable((T)ctx.lookup(REALMS));
        }  catch(NameNotFoundException ne){
            log.info("Name not found in context:{}",name);
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private InitialContext getContext() throws NamingException {
        return new InitialContext();
    }

    private void safeUnbind(String name) {
        try {
            InitialContext ic = getContext();
            ic.unbind(name);
        } catch (NamingException e) {
            throw new RuntimeException(e);

        }

    }

    public void getName() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

}
