package com.github.yktakaha4.quarto.application;

import java.util.logging.LogManager;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

@ApplicationPath("/api")
public class Application extends ResourceConfig {
  static {
    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.install();
  }

  public Application() {
    packages(this.getClass().getPackage().getName());
  }

}
