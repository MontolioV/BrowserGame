package com.myapp.objects;

import com.myapp.geometry.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * <p>Created by MontolioV on 28.11.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameObjectTest {
    private GameObject gameObject1 = new GameObjectTestImpl();
    private GameObject gameObject2 = new GameObjectTestImpl();
    @Mock
    private Position position1;
    @Mock
    private Position position2;
    private int radius = 250;

    @Before
    public void setUp() {
        gameObject1.setHitDamage(10);
        gameObject1.setHp(100);
        gameObject1.setHitRadius(radius);
        gameObject1.setCurrentPosition(position1);
        when(position1.getX()).thenReturn(0);
        when(position1.getY()).thenReturn(0);

        gameObject2.setHitDamage(10);
        gameObject2.setHp(100);
        gameObject2.setHitRadius(radius);
        gameObject2.setCurrentPosition(position2);
    }

    @Test
    public void idAutoAssignment() {
        int id = new GameObjectTestImpl().getId();
        assertThat(gameObject1.getId(), is(id - 2));
        assertThat(gameObject2.getId(), is(id - 1));
        assertThat(new GameObjectTestImpl().getId(), is(id + 1));
    }

    @Test
    public void objectsCollideCheck() {
        when(position2.getX()).thenReturn(300);
        when(position2.getY()).thenReturn(400);

        assertTrue(gameObject1.objectsCollideCheck(gameObject2));

        when(position2.getX()).thenReturn(301);
        when(position2.getY()).thenReturn(401);

        assertFalse(gameObject1.objectsCollideCheck(gameObject2));
    }

    @Test
    public void reduceHP() {
        gameObject1.reduceHP(10);
        assertThat(gameObject1.getHp(), is(90));
    }

    private class GameObjectTestImpl extends GameObject {
    }
}