package com.myapp.core;

import com.myapp.geometry.Position;
import com.myapp.network.messages.Request;
import com.myapp.network.messages.ResponseInit;
import com.myapp.network.messages.ResponseType;
import com.myapp.objects.PlayerCharacter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
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
    @Inject
    private Jsonb jsonb;
    @Inject
    private GameServer gameServer;
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
                    Thread.sleep(100);
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

    public String enterGame(Session session) {
        PlayerCharacter newPC = gameServer.addPC("some name");
        sessionPCMap.put(session, newPC);

        ResponseInit responseInit = new ResponseInit(ResponseType.INIT, newPC.getId());
        return jsonb.toJson(responseInit);
    }

    public void processClientInput(Session session, String input) {
        PlayerCharacter pc = sessionPCMap.get(session);
        Request request = jsonb.fromJson(input, Request.class);
        switch (request.getType()) {

            case MOVE:
                Position newDestination = jsonb.fromJson(request.getContent(), Position.class);
                pc.changeDestination(newDestination);
                break;
            case FIRE:
                // TODO: 07.12.18 Game cycle must not be run while PC is firing, otherwise PC can kill himself 
                pc.fire().forEach(gameServer::add);
                break;
        }

    }

    //Getter & Setters

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
