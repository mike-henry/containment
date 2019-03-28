package com.spx.containment.chain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.spx.containment.chain.model.SupplyChainLink;
import com.spx.containment.model.Container;
import com.spx.product.model.Product;

public class SupplyChainRepository {

  private List<SupplyChainLink> supplyChains = new ArrayList<SupplyChainLink>();

  public void addSupplyLink(Product product, Container from, Container to) {
    supplyChains.add(new SupplyChainLink(from, to, product));
  }

  public Optional<SupplyChainLink> getSupplyChainFor(Container to, Product wanted) {
    return supplyChains
        .stream()
        .filter(chain -> chain.getProduct().equals(wanted))
        .filter(chain -> chain.getTo().equals(to))
        .findFirst();
  }

}
