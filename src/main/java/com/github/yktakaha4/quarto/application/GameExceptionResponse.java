package com.github.yktakaha4.quarto.application;

public class GameExceptionResponse {
  private String reason;

  public GameExceptionResponse(String reason) {
    this.reason = reason;
  }

  public String getResult() {
    return "error";
  }

  public String getReason() {
    return reason;
  }

}
