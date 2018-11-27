package com.myapp;

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
        engine.enterGame(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(session.getId() + " message: " + message);
    }

    @OnError
    public void onError(Throwable e) {
    }

    @OnClose
    public void onClose(Session session) {
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}