package com.github.yktakaha4.quarto.application;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.yktakaha4.quarto.model.GameException;

@Provider
public class GameExceptionMapper implements ExceptionMapper<GameException> {

  private final Logger logger = LoggerFactory.getLogger(GameExceptionMapper.class);

  @Override
  public Response toResponse(GameException exception) {
    // TODO 自動生成されたメソッド・スタブ
    logger.info(exception.getMessage());
    GameExceptionResponse response = new GameExceptionResponse(exception.getMessage());
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(response).build();
  }

}
