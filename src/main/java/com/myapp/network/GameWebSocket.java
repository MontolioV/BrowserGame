package com.myapp.network;

import com.myapp.core.Engine;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
@ServerEndpoint("/game-socket")
public class GameWebSocket {
    @Inject
    private Engine engine;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println(session.getId() + " new client");
        String response = engine.enterGame(session);
        session.getBasicRemote().sendText(response);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        engine.processClientInput(session, message);
    }

    @OnError
    public void onError(Throwable e) {
    }

    @OnClose
    public void onClose(Session session) {
    }

    //Getter & Setters

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
