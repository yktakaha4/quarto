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

@Path("/display")
public class Display {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public DisplayResponse getDisplayResponse(@Valid @NotNull DisplayRequest request) throws GameException {
    DisplayResponse response = new DisplayResponse();

    Game game = GameManager.getInstance().getGame(request.getRoom(), request.getPlayer());

    response.setRequest(request);
    response.setGameState(game.getGameState());
    response.setPlayerA(game.isPlayerA(request.getPlayer()));
    response.setPlayerB(game.isPlayerB(request.getPlayer()));

    if (game.isPlaying()) {
      response.setBoard(game.getBoard());
      response.setChosen(game.getChosen());
      response.setStack(game.getStack());

    }

    return response;
  }

}
