package com.myapp.objects;

import com.myapp.geometry.Position;

import javax.json.bind.annotation.JsonbTransient;
import java.time.Clock;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public abstract class DynamicObject extends GameObject {
    @JsonbTransient
    private Clock clock;
    @JsonbTransient
    private long timeOfLastMove;
    private Position destination;
    private double speed;
    private double directionAngle;

    public DynamicObject() {
    }

    public DynamicObject(Position current, Position destination, int hitRadius, int hitDamage, int hp, double speed, Clock clock) {
        super(current, hitRadius, hitDamage, hp);
        this.destination = destination;
        this.speed = speed;
        this.clock = clock;
    }

    public void updatePosition() {
        Position selfPosition = getCurrentPosition();
        double remainingX = destination.getX() - selfPosition.getX();
        double remainingY = destination.getY() - selfPosition.getY();
        if (remainingX == 0 && remainingY == 0) {
            return;
        }

        long time = clock.millis() - timeOfLastMove;
        double maxPossibleDistance = (speed / 1000) * time;
        double destinationDistance = Math.sqrt(Math.pow(remainingX, 2) + Math.pow(remainingY, 2));
        if (destinationDistance <= maxPossibleDistance) {
            selfPosition.adjust(destination.getX() - selfPosition.getX(), destination.getY() - selfPosition.getY());
        } else {
            double distanceCoefficient = maxPossibleDistance / destinationDistance;
            selfPosition.adjust((int) (remainingX * distanceCoefficient), (int) (remainingY * distanceCoefficient));
        }

        timeOfLastMove = clock.millis();
    }

    public void changeDestination(Position newDestination) {
        timeOfLastMove = clock.millis();
        destination = newDestination;
    }

    public void updateDirectionAngle() {
        Position currentPosition = getCurrentPosition();
        if (currentPosition.distance(destination) > 0) {
            int xDestinationFromZero = destination.getX() - currentPosition.getX();
            int yDestinationFromZero = destination.getY() - currentPosition.getY();
            directionAngle = Math.atan2(yDestinationFromZero, xDestinationFromZero);
        }
    }

    //Getter & Setters

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getTimeOfLastMove() {
        return timeOfLastMove;
    }

    public void setTimeOfLastMove(long timeOfLastMove) {
        this.timeOfLastMove = timeOfLastMove;
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

    public double getDirectionAngle() {
        return directionAngle;
    }

    public void setDirectionAngle(double directionAngle) {
        this.directionAngle = directionAngle;
    }
}
