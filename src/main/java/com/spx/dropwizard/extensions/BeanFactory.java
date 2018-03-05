package com.spx.dropwizard.extensions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Singleton;




public class BeanFactory {

    protected <T> Bean<T> createBean( BeanManager beanManager,T instance) {
    
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) instance.getClass();
        AnnotatedType<T> at = (AnnotatedType<T>) beanManager.createAnnotatedType(type);
        final InjectionTarget<T> injectionTarget = beanManager.createInjectionTarget(at);
        
        return new Bean<T>() {
    
    
            @Override
            public Class<?> getBeanClass() {
                return type;
            }
    
    
            @Override
            public Set<InjectionPoint> getInjectionPoints() {
    
                return injectionTarget.getInjectionPoints();
    
            }
    
    
            @Override
            public String getName() {
                return Character.toLowerCase(type.getName().charAt(0)) + type.getName().substring(1);
            }
    
    
            @SuppressWarnings("serial")
            @Override
            public Set<Annotation> getQualifiers() {
                Set<Annotation> qualifiers = new HashSet<Annotation>();
                qualifiers.add( new AnnotationLiteral<Default>() {} );
                qualifiers.add( new AnnotationLiteral<Any>() {} );
                return qualifiers;
            }
    
    
            @Override
            public Class<? extends Annotation> getScope() {
                return Singleton.class;
            }
    
    
            @Override
            public Set<Class<? extends Annotation>> getStereotypes() {
                return Collections.emptySet();
            }
    
    
            @Override
            public Set<Type> getTypes() {
                Set<Type> types = new HashSet<Type>();
                types.add(type);
                types.add(Object.class);
                return types;
            }
    
    
            @Override
            public boolean isAlternative() {
                return false;
            }
    
    
            @Override
            public boolean isNullable() {
                return false;
            }
    
    
            @Override
    
            public T create(CreationalContext<T> ctx) {
                injectionTarget.inject(instance, ctx);
                injectionTarget.postConstruct(instance);
                return instance;
            }
    
    
            @Override
    
            public void destroy(T instance, CreationalContext<T> ctx) {
                injectionTarget.preDestroy(instance);
                injectionTarget.dispose(instance);
                ctx.release();
    
            }
    
    
        };
    }

}
