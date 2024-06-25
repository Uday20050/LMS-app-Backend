package com.manthatech.LMSApp.service;

public class LoginResponse {

    private final String token;

    private final long expiresIn;

    private final boolean isDefaultPassword;

    private String role;

    public LoginResponse(String token, long expiresIn, boolean isDefaultPassword, String role) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.isDefaultPassword = isDefaultPassword;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public boolean isDefaultPassword() {
        return isDefaultPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
