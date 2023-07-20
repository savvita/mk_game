package com.savita.machi_koro.db.models;

public class Auth {
    private final String username;
    private final String password;
    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean validate() {
        if(username.isBlank() || password.isBlank()) return false;
        return true;
    }
}
