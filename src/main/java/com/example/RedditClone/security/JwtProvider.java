package com.example.RedditClone.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

import java.sql.Date;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
public class  JwtProvider {

    private final SecretKey secretKey;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Autowired
    public JwtProvider(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken (Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(secretKey)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public boolean validateToken(String token){
        parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);

        return true;
    }

    public String getUsernameFromToken(String token){
        Claims claims = parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(secretKey)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
