package com.example.task3.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Data
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration}")
    private long jwtExpirationDate;

    private Key key(){

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;

    }

    public String getData(String token){
        return Jwts.parserBuilder()
                .setSigningKey((SecretKey) key())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();

    }

    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey((SecretKey) key())
                .build().parse(token);
        return true;
    }




}