package com.savita.machi_koro.server;

import com.savita.machi_koro.db.models.User;

public class AuthResponse {
    private ResponseCodes code;
    private User user;
    public AuthResponse(ResponseCodes code) {
        this(code, null);
    }

    public AuthResponse(ResponseCodes code, User user) {
        this.code = code;
        this.user = user;
    }

    public ResponseCodes getCode() {
        return code;
    }

    public void setCode(ResponseCodes code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
