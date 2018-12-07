package com.myapp.objects;

import com.myapp.geometry.Position;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Clock;

/**
 * <p>Created by MontolioV on 07.12.18.
 */
@Singleton
public class GameObjectFactory {
    @Inject
    private Clock clock;

    public PlayerCharacter createPCCommon(String name, Position current, Position destination, Weapon weapon) {
        PlayerCharacter result = new PlayerCharacter(current, destination, 10, 30, 100, 100, clock, name, weapon);
        result.setTimeOfLastMove(clock.millis());
        return result;
    }

    public Projectile createProjectileCommon(Position current, Position destination) {
        Projectile result = new Projectile(current, destination, 5, 100, 1, 150, clock);
        result.setTimeOfLastMove(clock.millis());
        return result;
    }

    //Getter & Setters

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
