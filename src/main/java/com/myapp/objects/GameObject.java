package com.myapp.objects;

import com.myapp.Position;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class GameObject {
    private Position currentPosition;
    private int hitRadius;
    private int hitDamage;
    private int hp;

    public GameObject() {
    }

    public GameObject(Position currentPosition, int hitRadius, int hitDamage, int hp) {
        this.currentPosition = currentPosition;
        this.hitRadius = hitRadius;
        this.hitDamage = hitDamage;
        this.hp = hp;
    }

    public boolean objectsCollideCheck(GameObject anotherGameObject) {
        Position positionThis = this.getCurrentPosition();
        Position positionAnother = anotherGameObject.getCurrentPosition();
        double xDiff = Math.pow((positionThis.getX() - positionAnother.getX()), 2);
        double yDiff = Math.pow((positionThis.getY() - positionAnother.getY()), 2);
        int distance = (int) Math.abs(Math.sqrt(xDiff + yDiff));
        distance -= this.getHitRadius();
        distance -= anotherGameObject.getHitRadius();
        return distance <= 0;
    }

    public void reduceHP(int hitDamage) {
        hp -= hitDamage;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getHitRadius() {
        return hitRadius;
    }

    public void setHitRadius(int hitRadius) {
        this.hitRadius = hitRadius;
    }

    public int getHitDamage() {
        return hitDamage;
    }

    public void setHitDamage(int hitDamage) {
        this.hitDamage = hitDamage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}
