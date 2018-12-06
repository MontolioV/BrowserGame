package com.myapp.objects;

import com.myapp.geometry.Position;

import java.time.Clock;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class Projectile extends DynamicObject {

    public Projectile() {
    }

    public Projectile(Position position, int hitRadius, int hitDamage, int hp, int speed, Clock clock) {
        super(position, hitRadius, hitDamage, hp, speed, clock);
    }
}
