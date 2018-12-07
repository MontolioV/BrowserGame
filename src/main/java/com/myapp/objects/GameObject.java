package com.myapp.objects;

import com.myapp.geometry.Position;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public abstract class GameObject {
    private static AtomicInteger objectCounter = new AtomicInteger();

    private int id;
    private Position currentPosition;
    private int hitRadius;
    private int hitDamage;
    private int hp;
    private String className = this.getClass().getSimpleName();

    public GameObject() {
        this.id = objectCounter.getAndIncrement();
    }

    public GameObject(Position currentPosition, int hitRadius, int hitDamage, int hp) {
        this();
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

    //Getter & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
