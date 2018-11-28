package com.myapp.objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p>Created by MontolioV on 28.11.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class DynamicObjectTest {
    private DynamicObject dynamicObject;
    @Mock
    private Position positionMock;
    @Mock
    private Position destinationMock;
    @Mock
    private Clock clockMock;

    @Before
    public void setUp() throws Exception {
        dynamicObject = new DynamicObject(positionMock, 0, 0, 0, 1, clockMock);
        dynamicObject.setTrajectoryChangeTime(0L);
        dynamicObject.setDestination(destinationMock);
        when(positionMock.getX()).thenReturn(0);
        when(positionMock.getY()).thenReturn(0);
        when(destinationMock.getX()).thenReturn(100);
        when(destinationMock.getY()).thenReturn(100);
        when(clockMock.millis()).thenReturn(1000L);
    }

    @Test
    public void updatePositionPositive() {
        dynamicObject.updatePosition();
        verify(positionMock).adjust(1, 1);

        when(clockMock.millis()).thenReturn(4999L);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(4, 4);
    }

    @Test
    public void updatePositionNegative() {
        when(destinationMock.getX()).thenReturn(-100);
        when(destinationMock.getY()).thenReturn(-100);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(-1, -1);
    }

    @Test
    public void updatePositionNegativeModifier() {
        when(destinationMock.getX()).thenReturn(-100);
        when(destinationMock.getY()).thenReturn(100);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(-1, 1);

        when(destinationMock.getX()).thenReturn(100);
        when(destinationMock.getY()).thenReturn(-100);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(1, -1);
    }

    @Test
    public void updatePositionZero() {
        when(destinationMock.getX()).thenReturn(0);
        when(destinationMock.getY()).thenReturn(100);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(0, 1);

        when(destinationMock.getX()).thenReturn(100);
        when(destinationMock.getY()).thenReturn(0);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(1, 0);

        when(destinationMock.getX()).thenReturn(0);
        when(destinationMock.getY()).thenReturn(0);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(0, 0);
    }

    @Test
    public void updatePositionAsymmetric() {
        dynamicObject.setSpeed(2);
        when(destinationMock.getX()).thenReturn(600);
        when(destinationMock.getY()).thenReturn(800);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(1, 2);
    }

    @Test
    public void updatePositionTooFast() {
        dynamicObject.setSpeed(1000);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(100, 100);
    }

    @Test
    public void changeDestination() {
        dynamicObject.setTrajectoryChangeTime(1L);
        assertThat(dynamicObject.getDestination(), is(destinationMock));

        dynamicObject.changeDestination(positionMock);

        assertThat(dynamicObject.getDestination(), is(positionMock));
        assertThat(dynamicObject.getTrajectoryChangeTime(), is(1000L));
    }
}