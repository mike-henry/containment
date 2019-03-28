package com.spx.session;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.deltaspike.data.api.EntityManagerResolver;
import com.spx.general.persistance.FilteredEntityScanner;

public class AuthorityEntityScanner extends FilteredEntityScanner implements EntityManagerResolver {


  @Inject
  @AuthorityAccess
  private EntityManager enityMAnager;

  public Class<? extends Annotation> getEntityGroup() {
    return AuthorityAccess.class;
  }

  @Override
  public EntityManager resolveEntityManager() {
    return enityMAnager;
  }

}
