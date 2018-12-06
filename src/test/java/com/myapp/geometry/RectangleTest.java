package com.myapp.geometry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * <p>Created by MontolioV on 05.12.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class RectangleTest {
    @Mock
    private Point pMock;
    @Mock
    private Point startPMock;
    @Mock
    private Point endPMock;

    @Test
    public void pointBelongsToArea() {
        when(startPMock.getX()).thenReturn(0);
        when(startPMock.getY()).thenReturn(0);
        when(endPMock.getX()).thenReturn(10);
        when(endPMock.getY()).thenReturn(10);
        Rectangle rectangle = new Rectangle(startPMock, endPMock);

        when(pMock.getX()).thenReturn(0);
        when(pMock.getY()).thenReturn(0);
        assertTrue(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(10);
        when(pMock.getY()).thenReturn(0);
        assertTrue(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(0);
        when(pMock.getY()).thenReturn(10);
        assertTrue(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(10);
        when(pMock.getY()).thenReturn(10);
        assertTrue(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(2);
        when(pMock.getY()).thenReturn(7);
        assertTrue(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(-1);
        when(pMock.getY()).thenReturn(-1);
        assertFalse(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(11);
        when(pMock.getY()).thenReturn(11);
        assertFalse(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(-1);
        when(pMock.getY()).thenReturn(11);
        assertFalse(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(11);
        when(pMock.getY()).thenReturn(-1);
        assertFalse(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(5);
        when(pMock.getY()).thenReturn(-1);
        assertFalse(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(5);
        when(pMock.getY()).thenReturn(11);
        assertFalse(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(11);
        when(pMock.getY()).thenReturn(5);
        assertFalse(rectangle.pointBelongsToArea(pMock));

        when(pMock.getX()).thenReturn(-1);
        when(pMock.getY()).thenReturn(5);
        assertFalse(rectangle.pointBelongsToArea(pMock));
    }
}