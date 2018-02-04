package com.github.yktakaha4.quarto.application;

import javax.validation.constraints.NotNull;

public class ChooseRequest {
  @NotNull
  private String room;

  @NotNull
  private String player;

  @NotNull
  private String chosen;

  public String getChosen() {
    return chosen;
  }

  public void setChosen(String chosen) {
    this.chosen = chosen;
  }

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
