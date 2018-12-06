package com.myapp.objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * <p>Created by MontolioV on 05.12.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerCharacterTest {
    @InjectMocks
    private PlayerCharacter pc;
    @Mock
    private Weapon weapon;
    private ArrayList<Projectile> result = new ArrayList<>();

    @Test
    public void fire() {
        when(weapon.fire(pc)).thenReturn(result);
        Collection<Projectile> projectiles = pc.fire();
        assertThat(projectiles, sameInstance(result));
    }
}