package com.spx.inventory.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import com.spx.containment.model.Container;
import com.spx.containment.services.ContainerServices;
import com.spx.inventory.model.Inventory;
import com.spx.inventory.repository.InventoryRepository;
import com.spx.product.model.Product;

@Named
@Transactional
public class InventoryLedger {

    private InventoryRepository repository;
    private ContainerServices containerServices;

    public InventoryLedger() {
    }

    @Inject
    public InventoryLedger(InventoryRepository repository,ContainerServices containerServices){
        this.repository=repository;
        this.containerServices=containerServices;
    }
    
    public void  addToContainer(Container container, Inventory inventory){
        this.repository.findByContainer(container)
       .add(inventory);
    }
    
    public List<Inventory> getContainerContentsOfProduct(Container container, Product product){
       return containerServices.getDecendants(container)
      .stream()
      .map(c -> repository.findByContainer(c))
      .flatMap(inventoryCollection -> inventoryCollection.stream())
      .filter(inventory -> inventory.getProduct().equals(product))
      .collect(Collectors.toList());
    }
    
    public boolean containerHasProduct(Product product, Container in) {        
        return thisContainerHasProduct(product,in) || theseContainersHaveProduct(product,in);
    }
    
    private boolean thisContainerHasProduct(Product product, Container container) {
        return this.repository.findByContainer(container)
        .stream()
        .filter(inv -> inv.getProduct().equals(product))
        .findFirst().isPresent();
    }
    
    private boolean theseContainersHaveProduct(Product product, Container in) {
        return in.getChildren()
        .stream().filter(c -> this.containerHasProduct(product, c))
        .collect(Collectors.toList())
        .isEmpty() == false;
    }

    @Transactional
    public void save(Inventory inventory) {
        this.repository.save(inventory);
    }

    public Collection<Inventory> findInventoryInContainer(Container container) {
     return this.repository.findByContainers(this.containerServices.getDecendants(container));
    }

}
