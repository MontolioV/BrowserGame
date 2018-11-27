package com.myapp.objects;

import com.myapp.Position;

import java.util.Objects;

/**
 * <p>Created by MontolioV on 26.11.18.
 */
public class PlayerCharacter extends DynamicObject {
    private String id;

    public PlayerCharacter() {
    }

    public PlayerCharacter(Position position, int hitRadius, int hitDamage, int hp, int speed, String id) {
        super(position, hitRadius, hitDamage, hp, speed);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerCharacter)) return false;
        PlayerCharacter playerCharacter = (PlayerCharacter) o;
        return id.equals(playerCharacter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
