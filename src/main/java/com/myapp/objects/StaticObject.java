package com.myapp.objects;

import com.myapp.geometry.Position;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public abstract class StaticObject extends GameObject {

    public StaticObject() {
    }

    public StaticObject(Position currentPosition, int hitRadius, int hitDamage, int hp) {
        super(currentPosition, hitRadius, hitDamage, hp);
    }
}
