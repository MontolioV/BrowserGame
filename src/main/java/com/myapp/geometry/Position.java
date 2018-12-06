package com.myapp.geometry;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class Position extends Point {

    public Position() {
    }

    public Position(int x, int y) {
        super(x, y);
    }

    public void adjust(int xModifier, int yModifier) {
        this.setX(this.getX() + xModifier);
        this.setY(this.getY() + yModifier);
    }
}
