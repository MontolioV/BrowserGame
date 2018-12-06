package com.myapp.geometry;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * <p>Created by MontolioV on 28.11.18.
 */
public class PositionTest {
    private Position position = new Position(1, 1);

    @Test
    public void adjust() {
        position.adjust(100, 100);
        assertThat(position.getX(), is(101));
        assertThat(position.getY(), is(101));

        position.adjust(-100, -100);
        assertThat(position.getX(), is(1));
        assertThat(position.getY(), is(1));
    }
}