package com.spx.containment.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.spx.containment.persistance.ContainmentAccess;
import lombok.Getter;
import lombok.Setter;

@Entity
@ContainmentAccess
@Getter
@Setter
public class Container extends Referenceable {

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
  private Set<Container> children = new HashSet<Container>();

  @ManyToOne
  private Container parent;

  public Set<Container> getChildren() {
    return Collections.unmodifiableSet(children);
  }

  public Optional<Container> getParent() {
    return Optional.ofNullable(parent);
  }

  public void setParent(Container newParent) {
    newParent.addChild(this);
  }

  public void addChild(Container child) {

    if (isAncestor(child)) {
      throw new IllegalArgumentException("Container in Ancestor");
    }

    if (child.getParent().isPresent()) {
      child.getParent().get().children.remove(child);
    }
    child.parent = this;
    children.add(child);
  }

  private boolean isAncestor(Container child) {
    return getAncestors().contains(child);
  }

  private List<Container> getAncestors() {
    Container presentAncestor = this;
    List<Container> result = new ArrayList<Container>();
    // result.add(presentAncestor);
    while (presentAncestor.getParent().isPresent()) {
      presentAncestor = presentAncestor.getParent().get();
      result.add(presentAncestor);
      if (presentAncestor instanceof Global) {
        break;
      }
    }
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Container other = (Container) obj;
    if (children == null) {
      if (other.children != null)
        return false;
    } else if (!children.equals(other.children))
      return false;
    if (parent == null) {
      if (other.parent != null)
        return false;
    } else if (!parent.equals(other.parent))
      return false;
    return true;
  }


}
