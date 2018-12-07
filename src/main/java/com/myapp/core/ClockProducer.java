package com.myapp.core;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.time.Clock;

/**
 * <p>Created by MontolioV on 28.11.18.
 */
public class ClockProducer {

    @Produces
    @Singleton
    public Clock produceClock() {
        return Clock.systemUTC();
    }
}
