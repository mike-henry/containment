package com.spx.containment.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Named;
import com.spx.containment.chain.model.SupplyChainLink;
import com.spx.containment.model.Container;
import com.spx.inventory.services.InventoryLedger;
import com.spx.product.model.Product;


@Named
public class SupplyChainService {

  private List<SupplyChainLink> supplyChains = new ArrayList<SupplyChainLink>();

  private final InventoryLedger ledger;

  @Inject
  public SupplyChainService(InventoryLedger ledger) {
    this.ledger = ledger;
  }

  public void addSupplyLink(Product product, Container from, Container to) {
    supplyChains.add(new SupplyChainLink(from, to, product));
  }

  public Optional<SupplyChainLink> getSupplyChainFor(Container to, Product wanted) {
    return this.supplyChains
        .stream()
        .filter(chain -> chain.getProduct().equals(wanted))
        .filter(chain -> chain.getTo().equals(to))
        .findFirst();
  }

}
