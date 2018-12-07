package com.myapp.network.messages;

/**
 * <p>Created by MontolioV on 03.12.18.
 */
public class ResponseInit extends Response {
    private int selfId;

    public ResponseInit() {
    }

    public ResponseInit(ResponseType type, int selfId) {
        super(type);
        this.selfId = selfId;
    }

    //Getter & Setters

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }
}
