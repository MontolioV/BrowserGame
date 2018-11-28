package com.myapp.objects;

import java.time.Clock;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class Projectile extends DynamicObject {
    private int damage;

    public Projectile() {
    }

    public Projectile(Position position, int hitRadius, int hitDamage, int hp, int speed, Clock clock, int damage) {
        super(position, hitRadius, hitDamage, hp, speed, clock);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
