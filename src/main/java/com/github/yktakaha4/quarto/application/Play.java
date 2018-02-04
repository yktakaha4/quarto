package com.github.yktakaha4.quarto.application;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.yktakaha4.quarto.model.Game;
import com.github.yktakaha4.quarto.model.GameException;
import com.github.yktakaha4.quarto.model.GameManager;

@Path("/play")
public class Play {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public PlayResponse getPlayResponse(@Valid @NotNull PlayRequest request) throws GameException {
    String room = UUID.nameUUIDFromBytes(request.getRoomName().getBytes()).toString();
    String player = request.getPlayer();

    Game game = GameManager.getInstance().createGame(room, player);

    PlayResponse response = new PlayResponse();
    response.setRequest(request);
    response.setGameState(game.getGameState());
    response.setRoom(room);
    response.setPlayer(player);

    if (game.isPlaying()) {
      if (game.isPlayerA(request.getPlayer())) {
        SocketUpdateScreen.sendUpdateMessage(game.getSessionIdB(), game.getRoom(), game.getPlayerB());
      } else if (game.isPlayerB(request.getPlayer())) {
        SocketUpdateScreen.sendUpdateMessage(game.getSessionIdA(), game.getRoom(), game.getPlayerA());
      }
    }

    return response;
  }

}
