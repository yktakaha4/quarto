package com.github.yktakaha4.quarto.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Game {
  private String room;

  private String playerA;
  private String sessionIdA;
  private String playerB;
  private String sessionIdB;

  private GameRule gameRule = new GameRule();
  private GameState gameState = GameState.WAITING;

  private String chosen;
  private Map<Position, Piece> board = new HashMap<>();
  private List<Piece> stack = new ArrayList<>();

  private LocalDateTime lastAccessTime = LocalDateTime.now();

  public Game(String room) {
    Objects.requireNonNull(room);

    this.room = room;
  }

  public synchronized void initialize() throws GameException {
    lastAccessTime = LocalDateTime.now();
    gameRule.initialize(this);
    this.gameState = this.gameState.next();
  }

  public synchronized void join(String player) throws GameException {
    lastAccessTime = LocalDateTime.now();
    Objects.requireNonNull(player);

    if (!isJoined(player)) {
      if (getPlayerA() == null) {
        setPlayerA(player);

      } else if (getPlayerB() == null && !getPlayerA().equals(player)) {
        setPlayerB(player);

      } else {
        throw new GameException("reject");

      }

    }
  }

  public synchronized void choose(String player, String pieceId) throws GameException {
    lastAccessTime = LocalDateTime.now();
    Optional<Piece> piece = findStack(pieceId);

    if (piece.isPresent()) {
      gameRule.choose(this, player, piece.get());

    } else {
      throw new GameException("failed_choose");

    }
    this.gameState = this.gameState.next();
  }

  public synchronized void put(String player, String pieceId, Position position) throws GameException {
    lastAccessTime = LocalDateTime.now();
    Optional<Piece> piece = findStack(pieceId);

    if (piece.isPresent()) {
      gameRule.put(this, pieceId, piece.get(), position);

    } else {
      throw new GameException("failed_put");

    }
    if (gameRule.check(this) != null) {
      this.gameState = gameState.win();
    } else {
      this.gameState = this.gameState.next();
    }
  }

  public boolean isExpired(LocalDateTime now) {
    return Duration.between(lastAccessTime, now).toMinutes() > 5;
  }

  public boolean isJoined(String player) {
    Objects.requireNonNull(player);
    return player.equals(getPlayerA()) || player.equals(getPlayerB());
  }

  public boolean isPlayerable() {
    return playerA != null && playerB != null && gameState.isWaiting();
  }

  public boolean isPlaying() {
    return gameState.isPlaying();
  }

  public String getRoom() {
    return room;
  }

  public String getPlayerA() {
    return playerA;
  }

  public boolean isPlayerA(String player) {
    return player.equals(playerA);
  }

  public String getPlayerB() {
    return playerB;
  }

  public boolean isPlayerB(String player) {
    return player.equals(playerB);
  }

  public GameState getGameState() {
    return gameState;
  }

  public Map<Position, Piece> getBoard() {
    return board;
  }

  public List<Piece> getStack() {
    return stack;
  }

  public String getChosen() {
    return chosen;
  }

  public void setPlayerA(String playerA) {
    this.playerA = playerA;
  }

  public void setPlayerB(String playerB) {
    this.playerB = playerB;
  }

  public void setChosen(String chosen) {
    this.chosen = chosen;
  }

  @Override
  public int hashCode() {
    return room.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null ? (hashCode() == obj.hashCode()) : false;
  }

  private Optional<Piece> findStack(String pieceId) {
    return stack.stream().filter((p) -> {
      return p.getId().equals(pieceId);
    }).findFirst();
  }

  public String getSessionIdA() {
    return sessionIdA;
  }

  public void setSessionIdA(String sessionIdA) {
    this.sessionIdA = sessionIdA;
  }

  public String getSessionIdB() {
    return sessionIdB;
  }

  public void setSessionIdB(String sessionIdB) {
    this.sessionIdB = sessionIdB;
  }

}
