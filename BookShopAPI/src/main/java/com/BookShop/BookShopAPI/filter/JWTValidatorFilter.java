//package com.BookShop.BookShopAPI.filter;
//
//import com.BookShop.BookShopAPI.entity.JWTToken;
//import com.BookShop.BookShopAPI.service.TokenBlacklistService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//public class JWTValidatorFilter extends OncePerRequestFilter {
//
//    private final TokenBlacklistService tokenBlacklistService;
//
//    public JWTValidatorFilter(TokenBlacklistService tokenBlacklistService) {
//        this.tokenBlacklistService = tokenBlacklistService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//        JWTToken token = new JWTToken(authorizationHeader);
//
//        if (authorizationHeader != null && !authorizationHeader.isEmpty()
//                && !tokenBlacklistService.isTokenBlacklisted(token)){
//
//            // kjo nese e fshijm accountin ton sepse tokeni mbet a useri u fshi
//            if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                tokenBlacklistService.blacklistToken(token);
//            }
//
//            Claims claims = Jwts.parser()
//                    .verifyWith(Keys.hmacShaKeyFor("jxgEQeXHuPq8VdbyYENkANdudQ53YUn4".getBytes(StandardCharsets.UTF_8)))
//                    .build()
//                    .parseSignedClaims(authorizationHeader)
//                    .getPayload();
//
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//            String authority = String.valueOf(claims.get("authorities"));
//
//            authority = authority.substring(1, authority.length() - 1);
//
//            authorities.add(new SimpleGrantedAuthority(authority));
//
//            SecurityContextHolder
//                    .getContext()
//                    .setAuthentication(
//                            new UsernamePasswordAuthenticationToken(
//                                    String.valueOf(claims.get("username")),
//                                    null,
//                                    authorities)
//                    );
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return request.getServletPath().equals("/login") || request.getServletPath().equals("/signup");
//    }
//}
//
//

package com.BookShop.BookShopAPI.filter;

import com.BookShop.BookShopAPI.entity.JWTToken;
import com.BookShop.BookShopAPI.service.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class JWTValidatorFilter extends OncePerRequestFilter {

    private final TokenBlacklistService tokenBlacklistService;

    public JWTValidatorFilter(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        JWTToken token = new JWTToken(authorizationHeader);

        if (authorizationHeader != null && !authorizationHeader.isEmpty()
                && !tokenBlacklistService.isTokenBlacklisted(token)){

            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor("jxgEQeXHuPq8VdbyYENkANdudQ53YUn4".getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(authorizationHeader)
                    .getPayload();

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            String authority = String.valueOf(claims.get("authorities"));
            authority = authority.substring(1, authority.length() - 1);
            authorities.add(new SimpleGrantedAuthority(authority));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    String.valueOf(claims.get("username")), null, authorities);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}