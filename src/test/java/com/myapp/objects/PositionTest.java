package com.myapp.objects;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * <p>Created by MontolioV on 28.11.18.
 */
public class PositionTest {
    private Position position = new Position(1, 1);
    private Position positionSame = new Position(1, 1);
    private Position positionX = new Position(2, 1);
    private Position positionY = new Position(1, 2);
    private Position positionBoth = new Position(2, 2);

    @Before
    public void setUp() throws Exception {
        position = new Position(1, 1);
    }

    @Test
    public void adjust() {
        position.adjust(100, 100);
        assertThat(position.getX(), is(101));
        assertThat(position.getY(), is(101));

        position.adjust(-100, -100);
        assertThat(position.getX(), is(1));
        assertThat(position.getY(), is(1));
    }

    @Test
    public void equals() {
        assertThat(position, is(positionSame));
        assertThat(position, not(positionX));
        assertThat(position, not(positionY));
        assertThat(position, not(positionX));
        assertThat(position, not(positionBoth));
    }
}