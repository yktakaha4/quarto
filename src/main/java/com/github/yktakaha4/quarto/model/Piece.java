package com.github.yktakaha4.quarto.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Piece {
  private String id = UUID.randomUUID().toString();
  private Set<Attribute> attributes;

  public Piece(Set<Attribute> attributes) {
    Objects.requireNonNull(attributes);
    this.attributes = Collections.unmodifiableSet(attributes);
  }

  public String getId() {
    return id;
  }

  public Set<Attribute> getAttributes() {
    return attributes;

  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null ? (hashCode() == obj.hashCode()) : false;
  }

}
