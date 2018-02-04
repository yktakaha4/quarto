package com.github.yktakaha4.quarto.application;

import javax.validation.constraints.NotNull;

public class DisplayRequest {
  @NotNull
  private String room;

  @NotNull
  private String player;

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

}
