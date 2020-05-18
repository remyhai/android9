package com.remy.firebasehello.model;

public class Note {

    String head = "";
    String body = "";
    String id = "";

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Note(String head, String body,String id) {
        this.head = head;
        this.body = body;
        this.id = id;
    }
}
