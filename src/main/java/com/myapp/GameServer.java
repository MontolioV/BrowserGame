package com.myapp;

import com.myapp.objects.*;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Created by MontolioV on 26.11.18.
 */


public class GameServer {
    private Jsonb jsonb = JsonbBuilder.create();
    private Set<DynamicObject> dynamicObjects = ConcurrentHashMap.newKeySet();
    private Set<StaticObject> staticObjects = ConcurrentHashMap.newKeySet();

    public void gameCycle() {
        dynamicObjects.parallelStream().forEach(DynamicObject::updatePosition);
        dynamicObjects.parallelStream()
                .forEach(dynamicObject -> {
                    dynamicObjects.parallelStream()
                            .filter(otherDynamicObject -> !otherDynamicObject.equals(dynamicObject))
                            .filter(dynamicObject::objectsCollideCheck)
                            .forEach(dynamicObjectOther -> {
                                dynamicObjectOther.reduceHP(dynamicObject.getHitDamage());
                            });
                    staticObjects.parallelStream()
                            .filter(dynamicObject::objectsCollideCheck)
                            .forEach(staticObject -> {
                                staticObject.reduceHP(dynamicObject.getHitDamage());
                                dynamicObject.reduceHP(staticObject.getHitDamage());
                            });
                });
        dynamicObjects.parallelStream().filter(dynamicObject -> dynamicObject.getHp() <= 0).forEach(dynamicObjects::remove);
        staticObjects.parallelStream().filter(staticObject -> staticObject.getHp() <= 0).forEach(staticObjects::remove);
    }

    public String toJson() {
        StringJoiner sj = new StringJoiner(",", "[", "]");

        dynamicObjects.forEach(dynamicObject -> sj.add(jsonb.toJson(dynamicObject)));
        staticObjects.forEach(staticObject -> sj.add(jsonb.toJson(staticObject)));
        return sj.toString();
    }

    public boolean add(DynamicObject dynamicObject) {
        return dynamicObjects.add(dynamicObject);
    }

    public boolean remove(DynamicObject dynamicObject) {
        return dynamicObjects.remove(dynamicObject);
    }

    public boolean add(StaticObject staticObject) {
        return this.staticObjects.add(staticObject);
    }

    public boolean remove(StaticObject staticObject) {
        return staticObjects.remove(staticObject);
    }

}
