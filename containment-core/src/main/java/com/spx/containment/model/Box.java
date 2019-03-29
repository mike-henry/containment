package com.spx.containment.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import com.spx.containment.persistance.ContainmentAccess;

@Entity
@ContainmentAccess
public class Box extends Container {

  private BigDecimal height = BigDecimal.ZERO;

  private BigDecimal width = BigDecimal.ZERO;

  private BigDecimal depth = BigDecimal.ZERO;

  public BigDecimal getHeight() {
    if (height == null)
      height = BigDecimal.ZERO;
    return height;
  }

  public BigDecimal getWidth() {
    if (width == null)
      width = BigDecimal.ZERO;
    return width;
  }

  public BigDecimal getDepth() {
    if (depth == null)
      depth = BigDecimal.ZERO;
    return depth;
  }

  public void setHeight(BigDecimal value) {
    height = value;

  }

  public void setWidth(BigDecimal value) {
    width = value;

  }

  public void setDepth(BigDecimal value) {
    depth = value;
  }

}
