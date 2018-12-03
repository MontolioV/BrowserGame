package com.myapp.network;

import com.myapp.objects.Position;

/**
 * <p>Created by MontolioV on 03.12.18.
 */
public class ResponseInit extends Response {
    private int selfId;
    private Position initPosition;

    public ResponseInit() {
    }

    public ResponseInit(ResponseType type, int selfId, Position initPosition) {
        super(type);
        this.selfId = selfId;
        this.initPosition = initPosition;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public Position getInitPosition() {
        return initPosition;
    }

    public void setInitPosition(Position initPosition) {
        this.initPosition = initPosition;
    }
}
