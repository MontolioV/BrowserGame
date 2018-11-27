package com.myapp.objects;

import com.myapp.Position;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class DynamicObject extends GameObject {
    private Position destination;
    private int speed;
    private long trajectoryChangeTime;

    public DynamicObject() {
    }

    public DynamicObject(Position position, int hitRadius, int hitDamage, int hp, int speed) {
        super(position, hitRadius, hitDamage, hp);
        destination = position;
        this.speed = speed;
    }

    public void updatePosition() {
        long time = System.currentTimeMillis() - trajectoryChangeTime;
        Position selfPosition = getCurrentPosition();
        int xModifier = (int) ((destination.getX() - selfPosition.getX()) / time);
        int yModifier = (int) ((destination.getY() - selfPosition.getY()) / time);
        selfPosition.adjust(xModifier, yModifier);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
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
}
