package com.myapp.objects;

import java.time.Clock;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerCharacter)) return false;
        PlayerCharacter playerCharacter = (PlayerCharacter) o;
        return playerName.equals(playerCharacter.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }
}
