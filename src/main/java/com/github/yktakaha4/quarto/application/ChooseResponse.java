package com.github.yktakaha4.quarto.application;

public class ChooseResponse {
  private ChooseRequest request;

  public String getResult() {
    return "ok";
  }

  public ChooseRequest getRequest() {
    return request;
  }

  public void setRequest(ChooseRequest request) {
    this.request = request;
  }

}
