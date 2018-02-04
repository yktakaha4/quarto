package com.github.yktakaha4.quarto.application;

import javax.validation.constraints.NotNull;

public class PutRequest {
  @NotNull
  private String room;

  @NotNull
  private String player;

  private int positionX;
  private int positionY;

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public String getPlayer() {
    return player;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public int getPositionX() {
    return positionX;
  }

  public void setPositionX(int positionX) {
    this.positionX = positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  public void setPositionY(int positionY) {
    this.positionY = positionY;
  }
}
