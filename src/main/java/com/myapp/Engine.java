package com.myapp;

import com.myapp.objects.PlayerCharacter;
import com.myapp.objects.Position;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Session;
import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
@Singleton
public class Engine {
    @Inject
    private Clock clock;
    private Jsonb jsonb = JsonbBuilder.create();
    private GameServer gameServer = new GameServer();
    private boolean engineRunning;
    private Map<Session, PlayerCharacter> sessionPCMap = new ConcurrentHashMap<>();

    public Engine() {
        startEngine();
    }

    public void startEngine() {
        if (engineRunning) {
            return;
        }

        engineRunning = true;

        Thread gameCycleThread = new Thread(() -> {
            while (engineRunning) {
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
            while (engineRunning) {
                try {
                    Thread.sleep(500);
                    sessionPCMap.entrySet().parallelStream().forEach(sessionPCEntry -> {
                        if (!sessionPCEntry.getKey().isOpen()) {
                            gameServer.remove(sessionPCEntry.getValue().getId());
                            sessionPCMap.remove(sessionPCEntry.getKey());
                        }
                    });
                    if (sessionPCMap.isEmpty()) {
                        continue;
                    }

                    String json = gameServer.toJson();
                    sessionPCMap.keySet().parallelStream().forEach(session -> session.getAsyncRemote().sendText(json));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        responsesThread.setDaemon(true);
        responsesThread.start();
    }

    public void stopEngine() {
        engineRunning = false;
    }

    public int enterGame(Session session) {
        Position position = new Position((int) (Math.random() * 500), (int) (Math.random() * 500));
        PlayerCharacter newPC = new PlayerCharacter(position, 10, 30, 100, 100, clock, "me");
        gameServer.add(newPC);
        sessionPCMap.put(session, newPC);
        return newPC.getId();
    }

    public void processClientInput(Session session, String input) {
        Position newDestination = jsonb.fromJson(input, Position.class);
        PlayerCharacter pc = sessionPCMap.get(session);
        pc.changeDestination(newDestination);
    }

    public boolean isEngineRunning() {
        return engineRunning;
    }

    public void setEngineRunning(boolean engineRunning) {
        this.engineRunning = engineRunning;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
