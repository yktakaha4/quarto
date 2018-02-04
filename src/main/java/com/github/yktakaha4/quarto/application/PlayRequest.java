package com.github.yktakaha4.quarto.application;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlayRequest {
  @NotNull
  @Size(min = 1, max = 50)
  private String roomName;

  @NotNull
  private String player;

  public String getRoomName() {
    return roomName;
  }

  public String getPlayer() {
    return player;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

}
