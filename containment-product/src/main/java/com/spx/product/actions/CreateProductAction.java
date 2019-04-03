package com.spx.product.actions;

import java.util.concurrent.Callable;
import com.spx.product.api.ProductView;
import com.spx.product.model.Product;
import com.spx.product.services.ProductManager;
import com.spx.product.services.ProductModelToViewMapper;

public class CreateProductAction implements Callable<Void> {

  private final ProductView[] views;
  private final ProductModelToViewMapper mapper;
  private final ProductManager productServices;

  public CreateProductAction(ProductManager productServices, ProductModelToViewMapper mapper, ProductView[] views) {
    this.views = views;
    this.mapper = mapper;
    this.productServices = productServices;
  }

  @Override
  public Void call() throws Exception {
    for (ProductView view : views) {
      Product product = mapper.createModelFromView(view);
      this.productServices.create(product);
    }
    return null;
  }

}
