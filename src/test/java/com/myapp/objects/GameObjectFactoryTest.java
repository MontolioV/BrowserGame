package com.myapp.objects;

import com.myapp.geometry.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * <p>Created by MontolioV on 07.12.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameObjectFactoryTest {
    @InjectMocks
    private GameObjectFactory factory;
    @Mock
    private Position positionMock;
    @Mock
    private Weapon weaponMock;
    @Mock
    private Clock clockMock;
    private String name = "name";

    @Before
    public void setUp() throws Exception {
        when(clockMock.millis()).thenReturn(1L);
    }

    @Test
    public void createPCCommon() {
        PlayerCharacter pcCommon = factory.createPCCommon(name, positionMock, positionMock, weaponMock);
        assertThat(pcCommon.getTimeOfLastMove(), is(1L));
        assertThat(pcCommon.getPlayerName(), is(name));
        assertThat(pcCommon.getWeapon(), is(weaponMock));
        assertThat(pcCommon.getClock(), is(clockMock));
        assertThat(pcCommon.getCurrentPosition(), is(positionMock));
        assertThat(pcCommon.getDestination(), is(positionMock));
        assertThat(pcCommon.getHitDamage(), is(30));
        assertThat(pcCommon.getHitRadius(), is(10));
        assertThat(pcCommon.getHp(), is(100));
        assertThat(pcCommon.getSpeed(), is(100D));
    }

    @Test
    public void createProjectileCommon() {
        Projectile projectileCommon = factory.createProjectileCommon(positionMock, positionMock);
        assertThat(projectileCommon.getTimeOfLastMove(), is(1L));
        assertThat(projectileCommon.getClock(), is(clockMock));
        assertThat(projectileCommon.getCurrentPosition(), is(positionMock));
        assertThat(projectileCommon.getDestination(), is(positionMock));
        assertThat(projectileCommon.getHitDamage(), is(100));
        assertThat(projectileCommon.getHitRadius(), is(5));
        assertThat(projectileCommon.getHp(), is(1));
        assertThat(projectileCommon.getSpeed(), is(150D));
    }
}