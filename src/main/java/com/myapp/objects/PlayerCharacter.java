package com.myapp.objects;

import java.time.Clock;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class PlayerCharacter extends DynamicObject {
    private String playerName;

    public PlayerCharacter() {
    }

    public PlayerCharacter(Position position, int hitRadius, int hitDamage, int hp, int speed, Clock clock, String playerName) {
        super(position, hitRadius, hitDamage, hp, speed, clock);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
