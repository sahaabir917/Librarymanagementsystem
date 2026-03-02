package com.infinitycodehubltd.librarymanagement.jwt;

import com.infinitycodehubltd.librarymanagement.user.Member;

public class AuthResponse {
    private String token;
    private String refreshToken;
    private String email;
    private long userId;
    private String name;
    private String phone;




    public AuthResponse(String token, String refreshToken, String email, long id, String phone) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.email = email;
        this.userId = id;
        this.phone = phone;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
