package com.github.yktakaha4.quarto.application;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    // TODO 自動生成されたメソッド・スタブ
    GameExceptionResponse response = new GameExceptionResponse(exception.getMessage());
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(response).build();
  }

}
