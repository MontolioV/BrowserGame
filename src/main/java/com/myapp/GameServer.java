package com.myapp;

import com.myapp.geometry.Point;
import com.myapp.geometry.Position;
import com.myapp.geometry.Rectangle;
import com.myapp.objects.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import java.time.Clock;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Created by MontolioV on 26.11.18.
 */

@Singleton
public class GameServer {
    @Inject
    private Jsonb jsonb;
    private Map<Integer, DynamicObject> dynamicObjects = new ConcurrentHashMap<>();
    private Map<Integer, StaticObject> staticObjects = new ConcurrentHashMap<>();
    private Rectangle levelArea = new Rectangle(new Point(10, 10), new Point(490, 490));

    public void gameCycle() {
        dynamicObjects.values().parallelStream().forEach(DynamicObject::updatePosition);
        dynamicObjects.values().parallelStream()
                .forEach(dynamicObject -> {
                    processCollisions(dynamicObject);
                    processLeavingLevel(dynamicObject);
                });
        dynamicObjects.values().parallelStream().filter(dynamicObject -> dynamicObject.getHp() <= 0)
                .mapToInt(GameObject::getId)
                .forEach(dynamicObjects::remove);
        staticObjects.values().parallelStream().filter(staticObject -> staticObject.getHp() <= 0)
                .mapToInt(GameObject::getId)
                .forEach(staticObjects::remove);
    }

    private void processCollisions(DynamicObject dynamicObject) {
        dynamicObjects.values().parallelStream()
                .filter(otherDynamicObject -> !otherDynamicObject.equals(dynamicObject))
                .filter(dynamicObject::objectsCollideCheck)
                .forEach(dynamicObjectOther -> {
                    dynamicObjectOther.reduceHP(dynamicObject.getHitDamage());
                });
        staticObjects.values().parallelStream()
                .filter(dynamicObject::objectsCollideCheck)
                .forEach(staticObject -> {
                    staticObject.reduceHP(dynamicObject.getHitDamage());
                    dynamicObject.reduceHP(staticObject.getHitDamage());
                });
    }

    private void processLeavingLevel(DynamicObject dynamicObject) {
        if (!levelArea.pointBelongsToArea(dynamicObject.getCurrentPosition())) {
            dynamicObject.setHp(0);
        }
    }

    public String toJson() {
        StringJoiner sj = new StringJoiner(",", "[", "]");

        dynamicObjects.values().forEach(dynamicObject -> sj.add(jsonb.toJson(dynamicObject)));
        staticObjects.values().forEach(staticObject -> sj.add(jsonb.toJson(staticObject)));
        return sj.toString();
    }

    public PlayerCharacter addPC(Clock clock) {
        Weapon weapon = new Weapon(levelArea.getDiagonal());
        Position position = new Position((int) (Math.random() * 500), (int) (Math.random() * 500));
        PlayerCharacter newPC = new PlayerCharacter(position, 10, 30, 100, 100, clock, "", weapon);
        add(newPC);
        return newPC;
    }

    public void add(DynamicObject dynamicObject) {
        dynamicObjects.put(dynamicObject.getId(), dynamicObject);
    }

    public void add(StaticObject staticObject) {
        staticObjects.put(staticObject.getId(), staticObject);
    }

    public void remove(int id) {
        dynamicObjects.remove(id);
        staticObjects.remove(id);
    }

    public boolean containsObject(int id) {
        return dynamicObjects.containsKey(id) || staticObjects.containsKey(id);
    }

    public Jsonb getJsonb() {
        return jsonb;
    }

    public void setJsonb(Jsonb jsonb) {
        this.jsonb = jsonb;
    }

    public Rectangle getLevelArea() {
        return levelArea;
    }

    public void setLevelArea(Rectangle levelArea) {
        this.levelArea = levelArea;
    }
}
