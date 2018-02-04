package com.github.yktakaha4.quarto.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {
  private final static GameManager instance = new GameManager();
  private Map<String, Game> games = new ConcurrentHashMap<>();
  private Timer timer = new Timer();

  private GameManager() {
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        LocalDateTime now = LocalDateTime.now();
        games.entrySet().stream().filter((entry) -> {
          return entry.getValue().isExpired(now);
        }).map((entry) -> {
          return entry.getKey();
        }).forEach(games::remove);
      }
    }, 1000 * 60 * 5, 1000 * 60 * 5);
  }

  public synchronized Game createGame(String room, String player) throws GameException {
    Game game = null;

    game = games.get(room);

    if (game == null) {
      game = new Game(room);
      games.put(room, game);
    }

    game.join(player);

    if (game.isPlayerable()) {
      game.initialize();
    }

    return game;
  }

  public synchronized Game getGame(String room, String player) throws GameException {
    Game game = games.get(room);

    if (game != null) {
      if (game.isJoined(player)) {
        return game;

      }

    }

    throw new GameException("cant get game");

  }

  public synchronized void joinSession(String room, String player, String sessionId) throws GameException {
    Game game = getGame(room, player);

    if (game.isPlayerA(player)) {
      game.setSessionIdA(sessionId);
    } else if (game.isPlayerB(player)) {
      game.setSessionIdB(sessionId);
    }
  }

  public static GameManager getInstance() {
    return instance;
  }

}
