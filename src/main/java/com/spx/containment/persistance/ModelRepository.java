package com.spx.containment.persistance;

import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import com.spx.containment.model.Referenceable;

public class ModelRepository {


  @Inject
  @ContainmentAccess
  private EntityManager em;


  public <M extends Referenceable> M save(M model) {
    if (model.getId() != null) {
      model = em.merge(model);
    } else if (!em.contains(model)) {
      model.setId(UUID.randomUUID());
      em.persist(model);
    }
    return model;
  }
}
