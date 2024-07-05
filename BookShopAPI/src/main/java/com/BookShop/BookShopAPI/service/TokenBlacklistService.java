package com.BookShop.BookShopAPI.service;

import com.BookShop.BookShopAPI.entity.JWTToken;
import com.BookShop.BookShopAPI.repository.JWTRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {

    private JWTRepository jwtRepository;

    public TokenBlacklistService(JWTRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    public JWTToken blacklistToken(JWTToken token) {
        return jwtRepository.save(token);
    }

    public boolean isTokenBlacklisted(JWTToken token) {
        JWTToken existingToken = jwtRepository.findJWTTokenByValue(token.getValue());
        return existingToken != null;
    }

}


















