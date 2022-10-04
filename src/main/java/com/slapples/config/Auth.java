package com.slapples.config;

import java.util.ArrayList;
import java.util.List;

// TODO: change to public static class?
public class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public long getTokenExpirationMsec() {
        return tokenExpirationMsec;
    }

    public void setTokenExpirationMsec(long tokenExpirationMsec) {
        this.tokenExpirationMsec = tokenExpirationMsec;
    }
}
