package com.myapp.objects;

import com.myapp.Position;
import com.myapp.TestJson;

/**
 * <p>Created by MontolioV on 27.11.18.
 */
public class TestJson2 extends TestJson {
    private int anInt;

    public TestJson2(Position position, int anInt) {
        super(position);
        this.anInt = anInt;
    }

    public TestJson2() {
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }
}
