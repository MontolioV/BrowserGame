package com.myapp.geometry;

/**
 * <p>Created by MontolioV on 05.12.18.
 */
public class Segment {
    private Point start;
    private Point end;
    private int a;
    private int b;
    private int c;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
        calculateConstants();
    }

    private void calculateConstants() {
        a = start.getX() - end.getX();
        b = end.getY() - start.getY();
        c = start.getX() * end.getY() - start.getY() * end.getX();
        xMin = Math.min(start.getX(), end.getX());
        xMax = Math.max(start.getX(), end.getX());
        yMin = Math.min(start.getY(), end.getY());
        yMax = Math.max(start.getY(), end.getY());
    }

    public boolean pointBelongs(Point p) {
        boolean result = false;
        boolean pointLiesOnLine = (a * p.getX() + b * p.getY() + c) == 0;
        if (pointLiesOnLine) {
            if ((p.getX() >= xMin && p.getX() <= xMax) && (p.getY() >= yMin && p.getY() <= yMax)) {
                result = true;
            }
        }
        return result;
    }

}
