package com.github.yktakaha4.quarto.application;

import java.io.IOException;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.github.yktakaha4.quarto.model.GameManager;

@ServerEndpoint("/socket/updateScreen")
public class SocketUpdateScreen {

  private static Queue<Session> sessions = new ConcurrentLinkedQueue<>();

  @OnOpen
  public void onOpen(Session session) {
    sessions.add(session);
  }

  @OnMessage
  public void onMessage(Session session, String message) {
    try {
      JSONObject jsonObject = new JSONObject(message);
      String room = jsonObject.getString("room");
      String player = jsonObject.getString("player");

      GameManager.getInstance().joinSession(room, player, session.getId());

    } catch (Exception e) {
      e.printStackTrace();

    }

  }

  @OnClose
  public void onClose(Session session) {
    sessions.remove(session);
  }

  @OnError
  public void onError(Session session, Throwable cause) {
    sessions.remove(session);
  }

  public static void sendUpdateMessage(String sessionId, String room, String player) {
    Optional<Session> session = sessions.stream().filter((s) -> {
      return s.getId().equals(sessionId);
    }).findFirst();
    if (session.isPresent()) {
      try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("room", room);
        jsonObject.put("player", player);
        session.get().getBasicRemote().sendText(jsonObject.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
