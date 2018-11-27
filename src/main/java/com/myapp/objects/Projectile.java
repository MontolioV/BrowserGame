package com.myapp.objects;

import com.myapp.Position;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class Projectile extends DynamicObject{
    private int damage;

    public Projectile() {
    }

    public Projectile(Position position, int hitRadius, int hitDamage, int hp, int speed, int damage) {
        super(position, hitRadius, hitDamage, hp, speed);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
