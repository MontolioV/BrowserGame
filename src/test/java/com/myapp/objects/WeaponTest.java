package com.myapp.objects;

import com.myapp.geometry.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * <p>Created by MontolioV on 06.12.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeaponTest {
    @Mock
    private PlayerCharacter pcMock;
    @Mock
    private Clock clockMock;
    @Mock
    private Position positionMock;

    @Before
    public void setUp() throws Exception {
        when(pcMock.getClock()).thenReturn(clockMock);
        when(pcMock.getCurrentPosition()).thenReturn(positionMock);
    }

    @Test
    public void fireFrom0Coordinate() {
        when(positionMock.getX()).thenReturn(0);
        when(positionMock.getY()).thenReturn(0);

        double range = Math.sqrt(2);//x0=0 y0=0 x1=1 y1=1
        Weapon weapon = new Weapon(range);

        testPosition(weapon, 135D, -1, 1);
        testPosition(weapon, 45D, 1, 1);
        testPosition(weapon, -45D, 1, -1);
        testPosition(weapon, -135D, -1, -1);

        weapon = new Weapon(1);

        testPosition(weapon, 180D, -1, 0);
        testPosition(weapon, 90D, 0, 1);
        testPosition(weapon, 0, 1, 0);
        testPosition(weapon, -90D, 0, -1);
        testPosition(weapon, -180D, -1, 0);
    }

    @Test
    public void fireFromOffset() {
        when(positionMock.getX()).thenReturn(1);
        when(positionMock.getY()).thenReturn(1);

        double range = Math.sqrt(2);//x0=0 y0=0 x1=1 y1=1
        Weapon weapon = new Weapon(range);

        testPosition(weapon, 135D, 0, 2);
        testPosition(weapon, 45D, 2, 2);
        testPosition(weapon, -45D, 2, 0);
        testPosition(weapon, -135D, 0, 0);

        weapon = new Weapon(1);

        testPosition(weapon, 180D, 0, 1);
        testPosition(weapon, 90D, 1, 2);
        testPosition(weapon, 0, 2, 1);
        testPosition(weapon, -90D, 1, 0);
        testPosition(weapon, -180D, 0, 1);
    }

    private void testPosition(Weapon weapon, double angle, int x, int y) {
        when(pcMock.getDirectionAngle()).thenReturn(angle);
        List<Projectile> projectiles = weapon.fire(pcMock);
        assertThat(projectiles.size(), is(1));
        assertThat(projectiles.get(0).getDestination().getX(), is(x));
        assertThat(projectiles.get(0).getDestination().getY(), is(y));
    }

}