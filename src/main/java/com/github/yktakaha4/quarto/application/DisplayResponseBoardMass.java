package com.github.yktakaha4.quarto.application;

import com.github.yktakaha4.quarto.model.Piece;
import com.github.yktakaha4.quarto.model.Position;

public class DisplayResponseBoardMass {
  private Position position;
  private Piece piece;

  public DisplayResponseBoardMass(Position position, Piece piece) {
    this.position = position;
    this.piece = piece;
  }

  public Position getPosition() {
    return position;
  }

  public Piece getPiece() {
    return piece;
  }

  public String getId() {
    return piece.getId();
  }

}
