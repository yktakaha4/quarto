package com.github.yktakaha4.quarto.model;

public enum GameState {
  WAITING, CHOOSE_PLAYER_A, CHOOSE_PLAYER_B, PUT_PLAYER_A, PUT_PLAYER_B, WIN_PLAYER_A, WIN_PLAYER_B, DRAW, ABORT;

  public GameState next() throws IllegalStateException {
    if (this == WAITING) {
      return (Math.random() > 0.5 ? CHOOSE_PLAYER_A : CHOOSE_PLAYER_B);

    } else if (this == CHOOSE_PLAYER_A) {
      return PUT_PLAYER_B;

    } else if (this == PUT_PLAYER_B) {
      return CHOOSE_PLAYER_B;

    } else if (this == CHOOSE_PLAYER_B) {
      return PUT_PLAYER_A;

    } else if (this == PUT_PLAYER_A) {
      return CHOOSE_PLAYER_A;

    } else if (this == WIN_PLAYER_A || this == WIN_PLAYER_B || this == DRAW) {
      return WAITING;

    }

    throw new IllegalStateException();
  }

  public GameState win() throws IllegalStateException {
    if (this == PUT_PLAYER_A) {
      return WIN_PLAYER_A;

    } else if (this == PUT_PLAYER_B) {
      return WIN_PLAYER_B;

    }

    throw new IllegalStateException();
  }

  public GameState reset() {
    return WAITING;
  }

  public boolean isWaiting() {
    return this == WAITING;
  }

  public boolean isChoosing() {
    return this == CHOOSE_PLAYER_A || this == CHOOSE_PLAYER_B;
  }

  public boolean isPutting() {
    return this == PUT_PLAYER_A || this == PUT_PLAYER_B;
  }

  public boolean isPlayerA() {
    return this == CHOOSE_PLAYER_A || this == PUT_PLAYER_A;
  }

  public boolean isPlayerB() {
    return this == CHOOSE_PLAYER_B || this == PUT_PLAYER_B;
  }

  public boolean isPlaying() {
    return !(this == WAITING || this == ABORT);
  }

}
