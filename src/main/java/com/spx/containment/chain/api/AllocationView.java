package com.spx.containment.chain.api;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AllocationView {
  private int quantity;
  private String inventoryReference;
  private String requestReference;
}
