package com.github.yktakaha4.quarto.application;

public class PutResponse {
  private PutRequest request;

  public String getResult() {
    return "ok";
  }

  public PutRequest getRequest() {
    return request;
  }

  public void setRequest(PutRequest request) {
    this.request = request;
  }

}
