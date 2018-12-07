package com.myapp.objects;

import com.myapp.geometry.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * <p>Created by MontolioV on 06.12.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeaponTest {
    @InjectMocks
    private Weapon weapon;
    @Mock
    private GameObjectFactory factoryMock;
    @Mock
    private Projectile projectileMock;
    @Mock
    private PlayerCharacter pcMock;
    @Mock
    private Clock clockMock;
    @Mock
    private Position positionMock;
    @Captor
    private ArgumentCaptor<Position> currentPositionCaptor;
    @Captor
    private ArgumentCaptor<Position> destinationPositionCaptor;
    private int projectileHitRadius = 5;
    private int pcHitRadius = 1;
    private int safeDistMod = projectileHitRadius + pcHitRadius + 5;

    @Before
    public void setUp() {
        when(pcMock.getClock()).thenReturn(clockMock);
        when(pcMock.getCurrentPosition()).thenReturn(positionMock);
        when(pcMock.getSpeed()).thenReturn(10D);
        when(pcMock.getHitRadius()).thenReturn(pcHitRadius);
        when(positionMock.getX()).thenReturn(0);
        when(positionMock.getY()).thenReturn(0);
    }

    @Test
    public void fireFrom0Coordinate() {
        double range = Math.sqrt(2);//x0=0 y0=0 x1=1 y1=1
        weapon.setRange(range);

        testPosition(weapon, 135, -safeDistMod, safeDistMod, -1, 1);
        testPosition(weapon, 45, safeDistMod, safeDistMod, 1, 1);
        testPosition(weapon, -45, safeDistMod, -safeDistMod, 1, -1);
        testPosition(weapon, -135, -safeDistMod, -safeDistMod, -1, -1);

        weapon.setRange(1);

        testPosition(weapon, 180, -safeDistMod, 0, -1, 0);
        testPosition(weapon, 90, 0, safeDistMod, 0, 1);
        testPosition(weapon, 0, safeDistMod, 0, 1, 0);
        testPosition(weapon, -90, 0, -safeDistMod, 0, -1);
        testPosition(weapon, -180, -safeDistMod, 0, -1, 0);
    }

    @Test
    public void fireFromOffset() {
        when(positionMock.getX()).thenReturn(1);
        when(positionMock.getY()).thenReturn(1);

        double range = Math.sqrt(2);//x0=0 y0=0 x1=1 y1=1
        weapon.setRange(range);

        testPosition(weapon, 135, 1 - safeDistMod, 1 + safeDistMod, 0, 2);
        testPosition(weapon, 45, 1 + safeDistMod, 1 + safeDistMod, 2, 2);
        testPosition(weapon, -45, 1 + safeDistMod, 1 - safeDistMod, 2, 0);
        testPosition(weapon, -135, 1 - safeDistMod, 1 - safeDistMod, 0, 0);

        weapon.setRange(1);

        testPosition(weapon, 180, 1 - safeDistMod, 1, 0, 1);
        testPosition(weapon, 90, 1, 1 + safeDistMod, 1, 2);
        testPosition(weapon, 0, 1 + safeDistMod, 1, 2, 1);
        testPosition(weapon, -90, 1, 1 - safeDistMod, 1, 0);
        testPosition(weapon, -180, 1 - safeDistMod, 1, 0, 1);
    }

    @Test
    public void fireAsymmetric() {
        weapon.setRange(1800);
        testPosition(weapon, 30, safeDistMod, safeDistMod, 1559, 900);
    }

    private void testPosition(Weapon weapon, int angle, int x0, int y0, int x1, int y1) {
        when(factoryMock.createProjectileCommon(any(Position.class), any(Position.class))).thenReturn(projectileMock);
        when(pcMock.getDirectionAngle()).thenReturn(Math.toRadians(angle));
        List<Projectile> projectiles = weapon.fire(pcMock);
        assertThat(projectiles, containsInAnyOrder(projectileMock));
        verify(factoryMock, atLeastOnce()).createProjectileCommon(currentPositionCaptor.capture(), destinationPositionCaptor.capture());
        assertThat(currentPositionCaptor.getValue().getX(), is(x0));
        assertThat(currentPositionCaptor.getValue().getY(), is(y0));
        assertThat(destinationPositionCaptor.getValue().getX(), is(x1));
        assertThat(destinationPositionCaptor.getValue().getY(), is(y1));
    }

}