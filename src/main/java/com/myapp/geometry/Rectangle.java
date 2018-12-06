package com.myapp.geometry;

/**
 * <p>Created by MontolioV on 05.12.18.
 */
public class Rectangle extends Area {
    private Point start;
    private Point end;
    private double diagonal;

    public Rectangle(Point start, Point end) {
        this.start = start;
        this.end = end;
        diagonal = start.distance(end);
    }

    @Override
    public boolean pointBelongsToArea(Point p) {
        return p.getX() >= start.getX() && p.getY() >= start.getY() && p.getX() <= end.getX() && p.getY() <= end.getY();
    }

    public double getDiagonal() {
        return diagonal;
    }
}
