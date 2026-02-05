package com.parkease.backend.dto;

public class AuthResponse {

    private String token;
    private String role;
    private String message;

    // 🔹 For LOGIN response
    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    // 🔹 For REGISTER or generic messages
    public AuthResponse(String message) {
        this.message = message;
    }

    // ===== Getters & Setters =====

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
