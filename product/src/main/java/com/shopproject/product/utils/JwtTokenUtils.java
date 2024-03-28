package com.shopproject.product.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    public String getLogin(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getId(String token){
        return (String) getAllClaimsFromToken(token).get("id");
    }

    public Claims getAllClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
