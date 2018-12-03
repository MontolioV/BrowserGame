package com.myapp;

import com.myapp.objects.DynamicObject;
import com.myapp.objects.StaticObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.json.bind.Jsonb;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServerTest {
    @InjectMocks
    private GameServer gameServer = new GameServer();
    @Mock
    private Jsonb jsonbMock;
    @Mock
    private DynamicObject do1Mock;
    @Mock
    private DynamicObject do2Mock;
    @Mock
    private StaticObject soMock;

    @Before
    public void setUp() throws Exception {
        when(do1Mock.getId()).thenReturn(0);
        when(do1Mock.getHitDamage()).thenReturn(1);
        when(do1Mock.getHp()).thenReturn(0);
        when(do1Mock.objectsCollideCheck(do2Mock)).thenReturn(true);
        when(do1Mock.objectsCollideCheck(soMock)).thenReturn(true);

        when(do2Mock.getId()).thenReturn(1);
        when(do2Mock.getHitDamage()).thenReturn(2);
        when(do2Mock.getHp()).thenReturn(1);
        when(do2Mock.objectsCollideCheck(do1Mock)).thenReturn(true);
        when(do2Mock.objectsCollideCheck(soMock)).thenReturn(false);

        when(soMock.getId()).thenReturn(2);
        when(soMock.getHp()).thenReturn(0);
        when(soMock.getHitDamage()).thenReturn(3);
        when(soMock.objectsCollideCheck(do1Mock)).thenReturn(true);
        when(soMock.objectsCollideCheck(do2Mock)).thenReturn(false);
    }

    @Test
    public void gameCycle() {
        gameServer.add(do1Mock);
        gameServer.add(do2Mock);
        gameServer.add(soMock);
        assertTrue(gameServer.containsObject(0));
        assertTrue(gameServer.containsObject(1));
        assertTrue(gameServer.containsObject(2));

        gameServer.gameCycle();
        assertFalse(gameServer.containsObject(0));
        assertTrue(gameServer.containsObject(1));
        assertFalse(gameServer.containsObject(2));
        verify(do1Mock).reduceHP(2);
        verify(do1Mock).reduceHP(3);
        verify(do2Mock).reduceHP(1);
        verify(do2Mock, never()).reduceHP(3);
        verify(soMock).reduceHP(1);
        verify(soMock, never()).reduceHP(2);
    }

    @Test
    public void toJson() {
        when(jsonbMock.toJson(any())).thenReturn("{obj}");
        gameServer.add(do1Mock);
        gameServer.add(soMock);

        String json = gameServer.toJson();
        assertThat(json, is("[{obj},{obj}]"));
    }
}