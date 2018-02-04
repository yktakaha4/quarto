package com.github.yktakaha4.quarto.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Attribute {
  ROUND(AttributeGroup.SHAPE), SQUARE(AttributeGroup.SHAPE), TALL(AttributeGroup.HEIGHT), SHORT(
      AttributeGroup.HEIGHT), WHITE(
          AttributeGroup.COLOR), BROWN(AttributeGroup.COLOR), FLAT(AttributeGroup.TOP), DENT(AttributeGroup.TOP);

  private AttributeGroup attributeGroup;

  private Attribute(AttributeGroup attributeGroup) {
    this.attributeGroup = attributeGroup;
  }

  public AttributeGroup getAttributeGroup() {
    return attributeGroup;
  }

  public static Set<Attribute> valuesOf(AttributeGroup attributeGroup) {
    return Stream.of(values()).filter((Attribute a) -> {
      return a.attributeGroup.equals(attributeGroup);
    }).collect(Collectors.toSet());
  }

}
