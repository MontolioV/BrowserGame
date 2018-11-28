package com.myapp;

import com.myapp.objects.DynamicObject;
import com.myapp.objects.GameObject;
import com.myapp.objects.StaticObject;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Created by MontolioV on 26.11.18.
 */


public class GameServer {
    private Jsonb jsonb = JsonbBuilder.create();
    private Map<Integer, DynamicObject> dynamicObjects = new ConcurrentHashMap<>();
    private Map<Integer, StaticObject> staticObjects = new ConcurrentHashMap<>();

    public void gameCycle() {
        dynamicObjects.values().parallelStream().forEach(DynamicObject::updatePosition);
        dynamicObjects.values().parallelStream()
                .forEach(dynamicObject -> {
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
                });
        dynamicObjects.values().parallelStream().filter(dynamicObject -> dynamicObject.getHp() <= 0)
                .mapToInt(GameObject::getId)
                .forEach(dynamicObjects::remove);
        staticObjects.values().parallelStream().filter(staticObject -> staticObject.getHp() <= 0)
                .mapToInt(GameObject::getId)
                .forEach(staticObjects::remove);
    }

    public String toJson() {
        StringJoiner sj = new StringJoiner(",", "[", "]");

        dynamicObjects.values().forEach(dynamicObject -> sj.add(jsonb.toJson(dynamicObject)));
        staticObjects.values().forEach(staticObject -> sj.add(jsonb.toJson(staticObject)));
        return sj.toString();
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
}
