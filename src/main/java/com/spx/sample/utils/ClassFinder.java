package com.spx.sample.utils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class ClassFinder {

    
    
    
    public  Stream<Class<?>> findClassesWithAnnotation(List<String> packageNames,Class<? extends Annotation> type) {
        return buildReflections(packageNames)
           .getTypesAnnotatedWith(type)
           .stream();       
    }

    
    public  Stream<Class<?>> findSubclassesOf(List<String> packageNames,Class<?> type) {
        
        Reflections reflections = buildReflections(packageNames);
        Set<Class<?>> result = new HashSet<Class<?>>();
        
        result.addAll( reflections.getSubTypesOf(type));       
        return result.stream(); 
 
    }


    private Reflections buildReflections(List<String> packageNames) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
             .setUrls(ClasspathHelper.forPackage("com.spx") )
             .forPackages(packageNames.toArray(new String[packageNames.size()]))
             .setScanners(new SubTypesScanner(), 
                          new TypeAnnotationsScanner())
             );
        return reflections;
    }

    
}
