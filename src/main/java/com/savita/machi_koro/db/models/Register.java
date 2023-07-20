package com.savita.machi_koro.db.models;

public class Register {
    private final String username;
    private final String password;
    private final String passwordConfirmation;
    public Register(String username, String password, String passwordConfirmation) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean validate() {
        if(username.isBlank() || password.isBlank()) return false;
        return password.equals(passwordConfirmation);
    }
}
