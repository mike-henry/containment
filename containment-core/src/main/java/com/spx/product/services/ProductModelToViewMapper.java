package com.spx.product.services;

import javax.inject.Named;
import com.spx.product.api.ProductView;
import com.spx.product.model.Product;

@Named
public class ProductModelToViewMapper {

  public Product createModelFromView(ProductView view) {
    Product product = new Product();
    product.setDescription(view.getDescription());
    product.setName(view.getName());
    product.setReference(view.getReference());
    return product;
  }

  public ProductView creatViewFromModel(Product product) {
    ProductView view = new ProductView();
    view.setDescription(product.getDescription());
    view.setName(product.getName());
    view.setReference(product.getReference());
    return view;
  }

}
