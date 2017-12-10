package com.oanabalaita.oana_maria.erasmuscom2.serverclient;

/**
 * Created by Oana-Maria on 23/04/2017.
 */

public class ServerRequest {
    private String operation;
    private User user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
