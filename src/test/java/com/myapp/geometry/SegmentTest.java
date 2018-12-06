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
public class SegmentTest {
    @Mock
    private Point pMock;
    @Mock
    private Point startPMock;
    @Mock
    private Point endPMock;

    @Test
    public void pointBelongs() {
        when(startPMock.getX()).thenReturn(0);
        when(startPMock.getY()).thenReturn(0);
        when(endPMock.getX()).thenReturn(2);
        when(endPMock.getY()).thenReturn(2);
        Segment segment = new Segment(startPMock, endPMock);

        when(pMock.getX()).thenReturn(0);
        when(pMock.getY()).thenReturn(0);
        assertTrue(segment.pointBelongs(pMock));

        when(pMock.getX()).thenReturn(1);
        when(pMock.getY()).thenReturn(1);
        assertTrue(segment.pointBelongs(pMock));

        when(pMock.getX()).thenReturn(2);
        when(pMock.getY()).thenReturn(2);
        assertTrue(segment.pointBelongs(pMock));

        when(pMock.getX()).thenReturn(-1);
        when(pMock.getY()).thenReturn(-1);
        assertFalse(segment.pointBelongs(pMock));

        when(pMock.getX()).thenReturn(0);
        when(pMock.getY()).thenReturn(1);
        assertFalse(segment.pointBelongs(pMock));

        when(pMock.getX()).thenReturn(1);
        when(pMock.getY()).thenReturn(0);
        assertFalse(segment.pointBelongs(pMock));

        when(pMock.getX()).thenReturn(3);
        when(pMock.getY()).thenReturn(3);
        assertFalse(segment.pointBelongs(pMock));
    }
}