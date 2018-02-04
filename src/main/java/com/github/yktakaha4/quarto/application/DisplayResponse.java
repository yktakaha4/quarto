package com.github.yktakaha4.quarto.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.yktakaha4.quarto.model.GameState;
import com.github.yktakaha4.quarto.model.Piece;
import com.github.yktakaha4.quarto.model.Position;

public class DisplayResponse {
  private DisplayRequest request;
  private GameState gameState;

  private String chosen;
  private List<DisplayResponseBoardMass> board = new ArrayList<>();
  private List<Piece> stack = new ArrayList<>();
  private boolean isPlayerA;
  private boolean isPlayerB;

  public String getResult() {
    return "ok";
  }

  public DisplayRequest getRequest() {
    return request;
  }

  public void setRequest(DisplayRequest request) {
    this.request = request;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  public String getChosen() {
    return chosen;
  }

  public void setChosen(String chosen) {
    this.chosen = chosen;
  }

  public List<DisplayResponseBoardMass> getBoard() {
    return board;
  }

  public void setBoard(List<DisplayResponseBoardMass> board) {
    this.board = board;
  }

  public void setBoard(Map<Position, Piece> board) {
    this.board = board.entrySet().stream().map((entry) -> {
      return new DisplayResponseBoardMass(entry.getKey(), entry.getValue());
    }).collect(Collectors.toList());
  }

  public List<Piece> getStack() {
    return stack;
  }

  public void setStack(List<Piece> stack) {
    this.stack = stack;
  }

  public boolean isPlayerA() {
    return isPlayerA;
  }

  public void setPlayerA(boolean isPlayerA) {
    this.isPlayerA = isPlayerA;
  }

  public boolean isPlayerB() {
    return isPlayerB;
  }

  public void setPlayerB(boolean isPlayerB) {
    this.isPlayerB = isPlayerB;
  }

}
