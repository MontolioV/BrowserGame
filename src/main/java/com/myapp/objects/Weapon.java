package com.myapp.objects;

import com.myapp.geometry.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by MontolioV on 05.12.18.
 */
public class Weapon {
    private double range;

    public Weapon() {
    }

    public Weapon(double range) {
        this.range = range;
    }

    public List<Projectile> fire(PlayerCharacter owner) {
        ArrayList<Projectile> result = new ArrayList<>(1);
        double radians = Math.toRadians(owner.getDirectionAngle());
        int x = (int) (range * Math.cos(radians)) + owner.getCurrentPosition().getX();
        int y = (int) (range * Math.sin(radians)) + owner.getCurrentPosition().getY();
        result.add(new Projectile(new Position(x, y), 5, 100, 1, 500, owner.getClock()));
        return result;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
