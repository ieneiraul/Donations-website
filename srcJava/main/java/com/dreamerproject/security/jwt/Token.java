package com.dreamerproject.security.jwt;

import com.dreamerproject.dto.UserProfileDTO;

import java.sql.Date;

public class Token {
    private String token;
    private java.sql.Date tokenExpirationAfterDays;
    private Object user;

    public Token(String token, java.sql.Date tokenExpirationAfterDays, Object userProfileDTO) {
        this.token = token;
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
        this.user = userProfileDTO;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }

    public void setTokenExpirationAfterDays(Date tokenExpirationAfterDays) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }


}
