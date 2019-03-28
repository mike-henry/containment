package com.spx.general.persistance;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;
import org.hibernate.boot.archive.scan.spi.MappingFileDescriptor;
import org.hibernate.boot.archive.scan.spi.PackageDescriptor;
import org.hibernate.boot.archive.scan.spi.ScanResult;

 final class ScannerAnnotationDecorator implements ScanResult {

    private final Class<? extends Annotation> entityGroupAnnotation;

    private final ScanResult subject;

    public ScannerAnnotationDecorator(ScanResult subject, Class<? extends Annotation> entityGroupAnnotation) {
        this.subject = subject;
        this.entityGroupAnnotation = entityGroupAnnotation;
    }

    @Override
    public Set<PackageDescriptor> getLocatedPackages() {
        return subject.getLocatedPackages();
    }

    @Override
    public Set<ClassDescriptor> getLocatedClasses() {
        return subject.getLocatedClasses()
        .stream()
        .filter(this::isOfGroup)
        .collect(Collectors.toSet());
    }

    private boolean isOfGroup(ClassDescriptor classDescriptor) {
        try {
            return  Class.forName(classDescriptor.getName()).getAnnotation(entityGroupAnnotation) != null;
        } catch (ClassNotFoundException classNotFound) {
            throw new RuntimeException(classNotFound);
        }
    }

    @Override
    public Set<MappingFileDescriptor> getLocatedMappingFiles() {
        return subject.getLocatedMappingFiles();
    }

}
