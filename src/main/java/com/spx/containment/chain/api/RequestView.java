package com.spx.containment.chain.api;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RequestView {

  private String reference;
  private String destination;

  private final List<AllocationView> allocations = new ArrayList<AllocationView>();
  private final List<BOMItemView> requiredItems = new ArrayList<BOMItemView>();

}
