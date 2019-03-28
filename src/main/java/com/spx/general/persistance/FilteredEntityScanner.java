package com.spx.general.persistance;

import java.lang.annotation.Annotation;
import org.hibernate.boot.archive.scan.internal.StandardScanner;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.hibernate.boot.archive.scan.spi.ScanParameters;
import org.hibernate.boot.archive.scan.spi.ScanResult;

public abstract class FilteredEntityScanner extends StandardScanner {

  public FilteredEntityScanner() {
    super();
  }

  @Override
  public ScanResult scan(ScanEnvironment environment, ScanOptions options, ScanParameters parameters) {
    ScanResult result = new ScannerAnnotationDecorator(super.scan(environment, options, parameters), getEntityGroup());
    return result;
  }

  protected abstract Class<? extends Annotation> getEntityGroup();

}
