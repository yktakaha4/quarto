package com.github.yktakaha4.quarto.application;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {

  private final Logger logger = LoggerFactory.getLogger(ThrowableMapper.class);

  @Override
  public Response toResponse(Throwable exception) {
    // TODO 自動生成されたメソッド・スタブ
    logger.error("http 500 error respond to client.", exception);
    return Response.status(500).entity(null).build();
  }

}
