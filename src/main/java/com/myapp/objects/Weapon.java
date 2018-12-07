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

        double angle = owner.getDirectionAngle();
        double xModifier = Math.cos(angle);
        double yModifier = Math.sin(angle);
        int x1 = (int) Math.round(range * xModifier + owner.getCurrentPosition().getX());
        int y1 = (int) Math.round(range * yModifier + owner.getCurrentPosition().getY());

        double xSign = xModifier < 0.1 && xModifier > -0.1 ? 0 : Math.signum(xModifier);
        double ySign = yModifier < 0.1 && yModifier > -0.1 ? 0 : Math.signum(yModifier);
        int x0 = (int) ((owner.getHitRadius() + 6) * xSign);
        int y0 = (int) ((owner.getHitRadius() + 6) * ySign);
        x0 += owner.getCurrentPosition().getX();
        y0 += owner.getCurrentPosition().getY();

        result.add(new Projectile(new Position(x0, y0), new Position(x1, y1), 5, 100, 1, owner.getSpeed() + 100, owner.getClock()));
        return result;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
