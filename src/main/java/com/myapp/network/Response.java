package com.myapp.network;

/**
 * <p>Created by MontolioV on 03.12.18.
 */
public class Response {
    private ResponseType type;

    public Response() {
    }

    public Response(ResponseType type) {
        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
