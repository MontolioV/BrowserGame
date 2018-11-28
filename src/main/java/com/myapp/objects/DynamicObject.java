package com.myapp.objects;

import javax.json.bind.annotation.JsonbTransient;
import java.time.Clock;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class DynamicObject extends GameObject {
    @JsonbTransient
    private Clock clock;
    private Position destination;
    private double speed;
    private long trajectoryChangeTime;

    public DynamicObject() {
    }

    public DynamicObject(Position position, int hitRadius, int hitDamage, int hp, int speed, Clock clock) {
        super(position, hitRadius, hitDamage, hp);
        destination = position;
        this.speed = speed;
        this.clock = clock;
    }

    public void updatePosition() {
        long time = clock.millis() - trajectoryChangeTime;
        Position selfPosition = getCurrentPosition();
        double remainingX = destination.getX() - selfPosition.getX();
        double remainingY = destination.getY() - selfPosition.getY();
        int xModifier = 0;
        int yModifier = 0;
        if (remainingX != 0 && remainingY != 0) {
            xModifier = signAndDestinationControl(remainingX,
                    (speed / 1000) * Math.abs(remainingX / remainingY) * time);
            yModifier = signAndDestinationControl(remainingY,
                    (speed / 1000) * Math.abs(remainingY / remainingX) * time);
        } else if (remainingX == 0 && remainingY != 0) {
            yModifier = signAndDestinationControl(remainingY, (speed / 1000) * time);
        } else if (remainingX != 0) {
            xModifier = signAndDestinationControl(remainingX, (speed / 1000) * time);
        }

        selfPosition.adjust(xModifier, yModifier);
    }

    private int signAndDestinationControl(double remaining, double modifier) {
        if (Math.abs(remaining) > modifier) {
            return (int) (modifier * Math.signum(remaining));
        } else {
            return (int) remaining;
        }
    }

    public void changeDestination(Position newDestination) {
        trajectoryChangeTime = clock.millis();
        destination = newDestination;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getTrajectoryChangeTime() {
        return trajectoryChangeTime;
    }

    public void setTrajectoryChangeTime(long trajectoryChangeTime) {
        this.trajectoryChangeTime = trajectoryChangeTime;
    }

    public Position getDestination() {
        return destination;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

}
