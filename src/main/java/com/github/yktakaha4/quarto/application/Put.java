package com.github.yktakaha4.quarto.application;

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
import com.github.yktakaha4.quarto.model.Position;

@Path("/put")
public class Put {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public PutResponse getPutResponse(@Valid @NotNull PutRequest request) throws GameException {
    PutResponse response = new PutResponse();

    Game game = GameManager.getInstance().getGame(request.getRoom(), request.getPlayer());

    Position position = new Position(request.getPositionX(), request.getPositionY());
    game.put(request.getPlayer(), game.getChosen(), position);

    response.setRequest(request);

    if (game.isPlayerA(request.getPlayer())) {
      SocketUpdateScreen.sendUpdateMessage(game.getSessionIdB(), game.getRoom(), game.getPlayerB());
    } else if (game.isPlayerB(request.getPlayer())) {
      SocketUpdateScreen.sendUpdateMessage(game.getSessionIdA(), game.getRoom(), game.getPlayerA());
    }

    return response;
  }

}
