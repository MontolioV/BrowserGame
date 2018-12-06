package com.myapp.objects;

import com.myapp.geometry.Position;

import java.time.Clock;
import java.util.Collection;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class PlayerCharacter extends DynamicObject {
    private String playerName;
    private Weapon weapon;

    public PlayerCharacter() {
    }

    public PlayerCharacter(Position position, int hitRadius, int hitDamage, int hp, int speed, Clock clock, String playerName, Weapon weapon) {
        super(position, hitRadius, hitDamage, hp, speed, clock);
        this.playerName = playerName;
        this.weapon = weapon;
    }


    public Collection<Projectile> fire() {
        return weapon.fire(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
