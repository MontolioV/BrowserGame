package com.myapp.core;

import com.myapp.geometry.Position;
import com.myapp.geometry.Rectangle;
import com.myapp.objects.*;
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
    private GameObjectFactory goFactoryMock;
    @Mock
    private DynamicObject do1Mock;
    @Mock
    private DynamicObject do2Mock;
    @Mock
    private StaticObject soMock;
    @Mock
    private Position positionMock;
    @Mock
    private Rectangle levelAreaMock;
    @Mock
    private PlayerCharacter pcMock;

    @Before
    public void setUp() throws Exception {
        when(do1Mock.getId()).thenReturn(0);
        when(do1Mock.getHitDamage()).thenReturn(1);
        when(do1Mock.getHp()).thenReturn(0);

        when(do2Mock.getId()).thenReturn(1);
        when(do2Mock.getHitDamage()).thenReturn(2);
        when(do2Mock.getHp()).thenReturn(1);

        when(soMock.getId()).thenReturn(2);
        when(soMock.getHp()).thenReturn(0);
        when(soMock.getHitDamage()).thenReturn(3);

        when(do1Mock.getCurrentPosition()).thenReturn(positionMock);
        when(do2Mock.getCurrentPosition()).thenReturn(positionMock);
        when(levelAreaMock.pointBelongsToArea(positionMock)).thenReturn(true);
    }

    @Test
    public void gameCycle() {
        when(do1Mock.objectsCollideCheck(do2Mock)).thenReturn(true);
        when(do1Mock.objectsCollideCheck(soMock)).thenReturn(true);
        when(do2Mock.objectsCollideCheck(do1Mock)).thenReturn(true);
        when(do2Mock.objectsCollideCheck(soMock)).thenReturn(false);
        when(soMock.objectsCollideCheck(do1Mock)).thenReturn(true);
        when(soMock.objectsCollideCheck(do2Mock)).thenReturn(false);

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

        verify(do1Mock).updateDirectionAngle();
        verify(do2Mock).updateDirectionAngle();
    }

    @Test
    public void gameCycleLeavingLevel() {
        gameServer.add(do2Mock);
        gameServer.gameCycle();
        verify(do2Mock, never()).setHp(anyInt());

        when(levelAreaMock.pointBelongsToArea(positionMock)).thenReturn(false);

        gameServer.gameCycle();
        verify(do2Mock).setHp(0);
        verify(do2Mock, never()).reduceHP(anyInt());
    }

    @Test
    public void toJson() {
        when(jsonbMock.toJson(any())).thenReturn("{obj}");
        gameServer.add(do1Mock);
        gameServer.add(soMock);

        String json = gameServer.toJson();
        assertThat(json, is("[{obj},{obj}]"));
    }

    @Test
    public void addPC() {
        when(goFactoryMock.createPCCommon(anyString(), any(Position.class), any(Position.class), any(Weapon.class))).thenReturn(pcMock);
        PlayerCharacter pc = gameServer.addPC("");
        assertThat(pc, is(pcMock));
        assertThat(gameServer.getDynamicObjects().size(), is(1));
        gameServer.getDynamicObjects().values().forEach(dynamicObject -> assertThat(dynamicObject, is(pcMock)));
    }
}