package com.spx.product.repository;

import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.FlushModeType;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;
import com.spx.containment.persistance.ContainerEntityScanner;
import com.spx.containment.persistance.ModelRepository;
import com.spx.product.model.Product;

@Repository
@EntityManagerConfig(entityManagerResolver = ContainerEntityScanner.class, flushMode = FlushModeType.COMMIT)
public abstract class ProductRepository extends AbstractEntityRepository<Product, UUID> {

  @Inject
  private ModelRepository saver;

  @Override
  public Product save(Product product) {
    return saver.save(product);
  }

  @Query("select p from Product p where p.reference=:reference")
  public abstract Optional<Product> findByReference(@QueryParam("reference") String reference);

  @Query("select p from Product p where p.name=:name")
  public abstract Optional<Product> findByName(@QueryParam("name") String name);


}
