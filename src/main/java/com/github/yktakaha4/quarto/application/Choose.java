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

@Path("/choose")
public class Choose {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ChooseResponse getChooseResponse(@Valid @NotNull ChooseRequest request) throws GameException {
    ChooseResponse response = new ChooseResponse();

    Game game = GameManager.getInstance().getGame(request.getRoom(), request.getPlayer());

    game.choose(request.getPlayer(), request.getChosen());

    response.setRequest(request);

    if (game.isPlayerA(request.getPlayer())) {
      SocketUpdateScreen.sendUpdateMessage(game.getSessionIdB(), game.getRoom(), game.getPlayerB());
    } else if (game.isPlayerB(request.getPlayer())) {
      SocketUpdateScreen.sendUpdateMessage(game.getSessionIdA(), game.getRoom(), game.getPlayerA());
    }

    return response;
  }

}
