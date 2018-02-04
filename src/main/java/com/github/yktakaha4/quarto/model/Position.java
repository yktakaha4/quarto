package com.github.yktakaha4.quarto.model;

public class Position {
  private int x;
  private int y;

  public Position(int x, int y) {

    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public int hashCode() {
    int result = 37;
    result = result * 31 + x;
    result = result * 31 + y;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null ? (hashCode() == obj.hashCode()) : false;
  }

}
