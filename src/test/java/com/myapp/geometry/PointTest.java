package com.myapp.geometry;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * <p>Created by MontolioV on 05.12.18.
 */
public class PointTest {
    private Point point = new Point(1, 1);
    private Point pointSame = new Point(1, 1);
    private Point pointDistance5 = new Point(4, 5);
    private Point pointX = new Point(2, 1);
    private Point pointY = new Point(1, 2);
    private Point pointBoth = new Point(2, 2);

    @Test
    public void distance() {
        double distance = point.distance(pointSame);
        assertThat(distance, is(0D));

        distance = point.distance(pointDistance5);
        assertThat(distance, is(5D));

        distance = pointDistance5.distance(point);
        assertThat(distance, is(5D));
    }

    @Test
    public void equals() {
        assertThat(point, is(pointSame));
        assertThat(point, not(pointX));
        assertThat(point, not(pointY));
        assertThat(point, not(pointX));
        assertThat(point, not(pointBoth));
    }
}