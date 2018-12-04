package com.myapp.objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

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
        dynamicObject = new DynamicObject(positionMock, 0, 0, 0, 100, clockMock);
        dynamicObject.setTimeOfLastMove(0L);
        dynamicObject.setDestination(destinationMock);
        when(positionMock.getX()).thenReturn(0);
        when(positionMock.getY()).thenReturn(0);
        when(destinationMock.getX()).thenReturn(707);
        when(destinationMock.getY()).thenReturn(707);   //Total 1000 path
        when(clockMock.millis()).thenReturn(1000L);
    }

    @Test
    public void updatePositionPositive() {
        dynamicObject.updatePosition();
        verify(positionMock).adjust(70, 70);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));

    }

    @Test
    public void updatePosition3Sec() {
        when(clockMock.millis()).thenReturn(3000L);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(212, 212);
        assertThat(dynamicObject.getTimeOfLastMove(), is(3000L));
    }

    @Test
    public void updatePositionNegative() {
        when(destinationMock.getX()).thenReturn(-707);
        when(destinationMock.getY()).thenReturn(-707);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(-70, -70);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));
    }

    @Test
    public void updatePositionMix() {
        when(destinationMock.getX()).thenReturn(-707);
        when(destinationMock.getY()).thenReturn(707);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(-70, 70);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));

        dynamicObject.setTimeOfLastMove(0L);
        when(destinationMock.getX()).thenReturn(707);
        when(destinationMock.getY()).thenReturn(-707);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(70, -70);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));
    }

    @Test
    public void updatePositionZero() {
        when(destinationMock.getX()).thenReturn(0);
        when(destinationMock.getY()).thenReturn(0);

        dynamicObject.updatePosition();
        verify(positionMock, never()).adjust(anyInt(), anyInt());
        assertThat(dynamicObject.getTimeOfLastMove(), is(0L));

        when(destinationMock.getX()).thenReturn(0);
        when(destinationMock.getY()).thenReturn(1000);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(0, 100);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));

        dynamicObject.setTimeOfLastMove(0L);
        when(destinationMock.getX()).thenReturn(1000);
        when(destinationMock.getY()).thenReturn(0);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(100, 0);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));
    }

    @Test
    public void updatePositionAsymmetric() {
        when(destinationMock.getX()).thenReturn(600);
        when(destinationMock.getY()).thenReturn(800);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(60, 80);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));
    }

    @Test
    public void updatePositionTooFast() {
        dynamicObject.setSpeed(10000);
        when(positionMock.getX()).thenReturn(507);
        when(positionMock.getY()).thenReturn(507);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(200, 200);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));

        dynamicObject.setTimeOfLastMove(0L);
        when(positionMock.getX()).thenReturn(-1000);
        when(positionMock.getY()).thenReturn(-1000);

        dynamicObject.updatePosition();
        verify(positionMock).adjust(1707, 1707);
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));
    }

    @Test
    public void changeDestination() {
        dynamicObject.setTimeOfLastMove(1L);
        assertThat(dynamicObject.getDestination(), is(destinationMock));

        dynamicObject.changeDestination(positionMock);

        assertThat(dynamicObject.getDestination(), is(positionMock));
        assertThat(dynamicObject.getTimeOfLastMove(), is(1000L));
    }

    @Test
    public void updateDirectionAngle() {
        when(positionMock.distance(destinationMock)).thenReturn(1D);
        when(destinationMock.getX()).thenReturn(-1);
        when(destinationMock.getY()).thenReturn(0);

        dynamicObject.updateDirectionAngle();
        assertThat(dynamicObject.getDirectionAngle(), is(180D));

        when(destinationMock.getX()).thenReturn(0);
        when(destinationMock.getY()).thenReturn(1);

        dynamicObject.updateDirectionAngle();
        assertThat(dynamicObject.getDirectionAngle(), is(90D));

        when(destinationMock.getX()).thenReturn(1);
        when(destinationMock.getY()).thenReturn(0);

        dynamicObject.updateDirectionAngle();
        assertThat(dynamicObject.getDirectionAngle(), is(0D));

        when(destinationMock.getX()).thenReturn(0);
        when(destinationMock.getY()).thenReturn(-1);

        dynamicObject.updateDirectionAngle();
        assertThat(dynamicObject.getDirectionAngle(), is(-90D));

        when(positionMock.getX()).thenReturn(1);
        when(positionMock.getY()).thenReturn(1);
        when(destinationMock.getX()).thenReturn(1);
        when(destinationMock.getY()).thenReturn(0);

        dynamicObject.updateDirectionAngle();
        assertThat(dynamicObject.getDirectionAngle(), is(-90D));

        when(positionMock.getX()).thenReturn(-1);
        when(positionMock.getY()).thenReturn(-1);
        when(destinationMock.getX()).thenReturn(1);
        when(destinationMock.getY()).thenReturn(1);

        dynamicObject.updateDirectionAngle();
        assertThat(dynamicObject.getDirectionAngle(), is(45D));

        when(positionMock.distance(destinationMock)).thenReturn(0D);

        dynamicObject.updateDirectionAngle();
        assertThat(dynamicObject.getDirectionAngle(), is(45D));
    }
}