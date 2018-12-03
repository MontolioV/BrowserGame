package com.myapp;

import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * <p>Created by MontolioV on 03.12.18.
 */
public class JsonbBuilderProducer {

    @Produces
    public Jsonb produce() {
        return JsonbBuilder.create();
    }
}
