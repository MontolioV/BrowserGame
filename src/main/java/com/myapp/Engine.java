package com.myapp;

import com.myapp.objects.PlayerCharacter;

import javax.inject.Singleton;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
@Singleton
public class Engine {
    private GameServer gameServer = new GameServer();
    private boolean gameIsRunning = true;
    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public Engine() {
        Thread gameCycleThread = new Thread(() -> {
            while (gameIsRunning) {
                try {
                    Thread.sleep(300);
                    gameServer.gameCycle();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameCycleThread.setDaemon(true);
        gameCycleThread.start();

        Thread responsesThread = new Thread(() -> {
            while (gameIsRunning) {
                try {
                    Thread.sleep(500);
                    String json = gameServer.toJson();
                    sessionMap.forEach((s, session) -> session.getAsyncRemote().sendText(json));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        responsesThread.setDaemon(true);
        responsesThread.start();
    }

    public void enterGame(Session session) {
        sessionMap.put(session.getId(), session);
        Position position = new Position((int) (Math.random() * 500), (int) (Math.random() * 500));
        gameServer.add(new PlayerCharacter(position, 10, 30, 100, 100, session.getId()));
    }
}
