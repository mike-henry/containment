package com.spx.product.actions;

import javax.inject.Inject;
import javax.inject.Named;
import com.spx.product.api.ProductView;
import com.spx.product.services.ProductManager;
import com.spx.product.services.ProductModelToViewMapper;

@Named
public class ProductActionFactory {

    private final ProductManager productServices;
    private final ProductModelToViewMapper mapper;

    @Inject
    public ProductActionFactory(ProductManager productServices, ProductModelToViewMapper mapper) {
        super();
        this.productServices = productServices;
        this.mapper = mapper;
    }

    public CreateProductAction buildCreateProductAction(ProductView[] containerViews) {
        CreateProductAction result = new CreateProductAction(productServices, mapper, containerViews);
        return result;
    }

    public GetProductAction buildGetProductAction(String reference) {
        return new GetProductAction(productServices,  mapper,reference);
    }

    public RemoveProductAction buildRemoveProductAction(String reference) {
        // TODO Auto-generated method stub
        return null;
    }

 

}
