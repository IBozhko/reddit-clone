package com.example.RedditClone.security;

public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private Long expirationMillis;

    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Long getTokenExpirationMillis() {
        return expirationMillis;
    }

    public void setTokenExpirationMillis(Long expirationMillis) {
        this.expirationMillis = expirationMillis;
    }
}
