package com.spx.containment.chain.repository;

import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Repository;
import com.spx.containment.chain.model.Request;
import com.spx.containment.persistance.ContainerEntityScanner;
import com.spx.containment.persistance.ContainmentAccess;
import com.spx.containment.persistance.ModelRepository;

@Repository

@EntityManagerConfig(entityManagerResolver = ContainerEntityScanner.class, flushMode = FlushModeType.COMMIT)
public abstract class RequestRepository extends AbstractEntityRepository<Request, UUID> {

  @Inject
  private ModelRepository saver;

  @Inject
  @ContainmentAccess
  private EntityManager em;

  @Override
  public Request save(Request product) {
    return saver.save(product);
  }

}
