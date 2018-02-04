package com.github.yktakaha4.quarto.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GameRule {

  private static int POSITION_MIN = 0;
  private static int POSITION_MAX = 3;
  private static int POSITION_LENGTH = POSITION_MAX + 1;

  public void initialize(Game game) throws GameException {
    Objects.requireNonNull(game);
    if (!game.getGameState().isWaiting()) {
      throw new GameException("failed_initialize");
    }

    game.getBoard().clear();

    for (Attribute color : Attribute.valuesOf(AttributeGroup.COLOR)) {
      for (Attribute height : Attribute.valuesOf(AttributeGroup.HEIGHT)) {
        for (Attribute shape : Attribute.valuesOf(AttributeGroup.SHAPE)) {
          for (Attribute top : Attribute.valuesOf(AttributeGroup.TOP)) {
            Set<Attribute> attributes = new HashSet<>();
            attributes.add(color);
            attributes.add(height);
            attributes.add(shape);
            attributes.add(top);

            game.getStack().add(new Piece(attributes));

          }

        }

      }

    }
  }

  public void choose(Game game, String player, Piece piece) throws GameException {
    Objects.requireNonNull(game);
    Objects.requireNonNull(player);
    Objects.requireNonNull(piece);

    if (!game.getGameState().isChoosing()) {
      throw new GameException("is_not_choosing");
    }
    if (!game.getStack().contains(piece)) {
      throw new GameException("not_contains_piece_in_stack");
    }
    if (game.getChosen() != null) {
      throw new GameException("piece_choosed");
    }

    game.setChosen(piece.getId());
  }

  public void put(Game game, String player, Piece piece, Position position) throws GameException {
    Objects.requireNonNull(game);
    Objects.requireNonNull(player);
    Objects.requireNonNull(piece);
    Objects.requireNonNull(position);

    if (!game.getGameState().isPutting()) {
      throw new GameException("is_not_putting");
    }
    if (!game.getStack().contains(piece)) {
      throw new GameException("not_contains_piece_in_stack");
    }
    if (game.getChosen() == null) {
      throw new GameException("piece_not_choosed");
    }
    if (!(position.getX() >= POSITION_MIN && position.getX() <= POSITION_MAX)) {
      throw new GameException("invalid_position_x");
    }
    if (!(position.getY() >= POSITION_MIN && position.getY() <= POSITION_MAX)) {
      throw new GameException("invalid_position_y");
    }

    game.getStack().remove(piece);
    game.getBoard().put(position, piece);
    game.setChosen(null);
  }

  public List<Entry<Position, Piece>> check(Game game) throws GameException {
    List<List<Entry<Position, Piece>>> chekingPieces = new ArrayList<List<Entry<Position, Piece>>>();

    for (int position = POSITION_MIN; position < POSITION_LENGTH; position++) {
      final int p = position;
      // 横
      chekingPieces.add(game.getBoard().entrySet().stream().filter((entry) -> {
        return entry.getKey().getX() == p;
      }).collect(Collectors.toList()));

      // 縦
      chekingPieces.add(game.getBoard().entrySet().stream().filter((entry) -> {
        return entry.getKey().getY() == p;
      }).collect(Collectors.toList()));
    }

    // 斜め
    chekingPieces.add(game.getBoard().entrySet().stream().filter((entry) -> {
      return entry.getKey().getX() == entry.getKey().getY();
    }).collect(Collectors.toList()));
    chekingPieces.add(game.getBoard().entrySet().stream().filter((entry) -> {
      return entry.getKey().getX() == (POSITION_MAX - entry.getKey().getY());
    }).collect(Collectors.toList()));

    // 列が全て埋まっているものを残す
    chekingPieces = chekingPieces.stream().filter((list) -> {
      return list.size() == POSITION_LENGTH;
    }).collect(Collectors.toList());

    //
    for (Attribute attribute : Attribute.values()) {
      Optional<List<Entry<Position, Piece>>> matches = chekingPieces.stream().filter((list) -> {
        return list.stream().allMatch((entry) -> {
          return entry.getValue().getAttributes().contains(attribute);
        });
      }).findFirst();
      if (matches.isPresent()) {
        return matches.get();
      }
    }
    return null;
  }

}
