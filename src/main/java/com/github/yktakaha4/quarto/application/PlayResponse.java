package com.github.yktakaha4.quarto.application;

import com.github.yktakaha4.quarto.model.GameState;

public class PlayResponse {
  private PlayRequest request;
  private GameState gameState;
  private String room;
  private String player;

  public String getResult() {
    return "ok";
  }

  public PlayRequest getRequest() {
    return request;
  }

  public void setRequest(PlayRequest request) {
    this.request = request;
  }

  public GameState getGameState() {
    return gameState;
  }

  public String getRoom() {
    return room;
  }

  public String getPlayer() {
    return player;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

}
