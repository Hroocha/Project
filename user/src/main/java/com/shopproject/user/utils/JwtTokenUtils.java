package com.shopproject.user.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime-min}")
    private Integer jwtLifeTimeMinutes;

    public String generateToken(UserDetails userDetails, UUID id) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifeTimeMinutes * 60 * 1000);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", String.valueOf(id))
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getLogin(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getId(String token) {
        return (String) getAllClaimsFromToken(token).get("id");
    }

}
