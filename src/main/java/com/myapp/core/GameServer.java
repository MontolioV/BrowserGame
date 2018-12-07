package com.myapp.core;

import com.myapp.geometry.Point;
import com.myapp.geometry.Position;
import com.myapp.geometry.Rectangle;
import com.myapp.objects.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Created by MontolioV on 26.11.18.
 */

@Singleton
public class GameServer {
    @Inject
    private GameObjectFactory gameObjectFactory;
    @Inject
    private Jsonb jsonb;
    private Map<Integer, DynamicObject> dynamicObjects = new ConcurrentHashMap<>();
    private Map<Integer, StaticObject> staticObjects = new ConcurrentHashMap<>();
    private Rectangle levelArea = new Rectangle(new Point(0, 0), new Point(500, 500));

    public void gameCycle() {
        dynamicObjects.values().parallelStream().forEach(dynamicObject -> {
            dynamicObject.updatePosition();
            dynamicObject.updateDirectionAngle();
        });
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

    public PlayerCharacter addPC(String name) {
        Weapon weapon = new Weapon(gameObjectFactory, levelArea.getDiagonal());
        Position position = new Position((int) (Math.random() * 500), (int) (Math.random() * 500));
        PlayerCharacter newPC = gameObjectFactory.createPCCommon(name, position, position, weapon);
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

    //Getter & Setters

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

    public GameObjectFactory getGameObjectFactory() {
        return gameObjectFactory;
    }

    public void setGameObjectFactory(GameObjectFactory gameObjectFactory) {
        this.gameObjectFactory = gameObjectFactory;
    }

    Map<Integer, DynamicObject> getDynamicObjects() {
        return dynamicObjects;
    }

    void setDynamicObjects(Map<Integer, DynamicObject> dynamicObjects) {
        this.dynamicObjects = dynamicObjects;
    }

    Map<Integer, StaticObject> getStaticObjects() {
        return staticObjects;
    }

    void setStaticObjects(Map<Integer, StaticObject> staticObjects) {
        this.staticObjects = staticObjects;
    }
}
