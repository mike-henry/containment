package com.spx.product.actions;

import java.util.concurrent.Callable;
import com.spx.product.services.ProductManager;

public class RemoveProductAction implements Callable<Void> {

    private final String reference;
    private final ProductManager productServices;

    public RemoveProductAction(String reference,ProductManager productServices) {
        this.reference = reference;
        this.productServices=productServices;
    }

    @Override
    public Void call() {
        this.productServices.removeProductByReference(reference);
        return null;
    }
}
