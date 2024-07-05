package com.BookShop.BookShopAPI.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

// 4:
public class JWTTokenGenerator {

    private static final String JWT_KEY = "jxgEQeXHuPq8VdbyYENkANdudQ53YUn4";
    private static final String JWT_HEADER = "Authorization";

    public static void generateJWTToken(Authentication authentication, HttpServletResponse response){
        String jwtToken = Jwts.builder()
                .issuer("BookShopAPI")
                .subject("JWT Token")
                .claim("username", authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 86400000)) // 86400000 milisecons -> 24h
                .signWith(Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();
        response.setHeader(JWT_HEADER, jwtToken);
    }
}
