package com.spx.product.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.spx.containment.business.exceptions.NotFoundException;
import com.spx.product.model.Product;
import com.spx.product.repository.ProductRepository;

@RequestScoped
public class ProductManager {

    private ProductRepository repository;

    public ProductManager() {
    }

    @Inject
    public ProductManager(ProductRepository repository) {
        this.repository = repository;
    }

    public void create(Product product) {
        this.repository.save(product);
    }

    public Product findByReference(String reference) {
        return this.repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Product Reference not Found"));
    }

    public void removeProductByReference(String reference) {
        Product product = findByReference(reference);
        this.repository.remove(product);
    }

    public Product findByName(String name) {
        return this.repository.findByName(name)
        .orElseThrow(() -> new NotFoundException("Product Name not Found"));
    }

}
