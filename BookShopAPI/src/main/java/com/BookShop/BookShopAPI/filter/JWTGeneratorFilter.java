package com.BookShop.BookShopAPI.filter;

import com.BookShop.BookShopAPI.entity.JWTToken;
import com.BookShop.BookShopAPI.service.TokenBlacklistService;
import com.BookShop.BookShopAPI.utility.JWTTokenGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 5:
public class JWTGeneratorFilter extends OncePerRequestFilter {

    @Lazy
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorizationHeader = request.getHeader("Authorization");

        if (authentication != null){

            JWTToken existingToken = new JWTToken(authorizationHeader);
            if (tokenBlacklistService.isTokenBlacklisted(existingToken))
                JWTTokenGenerator.generateJWTToken(authentication, response);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
