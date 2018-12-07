package com.myapp.network.messages;

/**
 * <p>Created by MontolioV on 07.12.18.
 */
public class Request {
    private RequestType type;
    private String content;

    //Getter & Setters

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
