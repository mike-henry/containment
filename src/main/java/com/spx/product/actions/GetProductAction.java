package com.spx.product.actions;

import java.util.concurrent.Callable;

import com.spx.product.api.ProductView;
import com.spx.product.services.ProductManager;
import com.spx.product.services.ProductModelToViewMapper;

public class GetProductAction implements Callable<ProductView> {

    private final String reference;
    private final ProductModelToViewMapper mapper;
    private final ProductManager productServices;

    public GetProductAction(ProductManager productServices, ProductModelToViewMapper mapper, String reference) {
        this.reference = reference;
        this.mapper = mapper;
        this.productServices = productServices;
    }

    @Override
    public ProductView call() throws Exception {
        return mapper.creatViewFromModel(productServices.findByReference(reference));
    }

}
