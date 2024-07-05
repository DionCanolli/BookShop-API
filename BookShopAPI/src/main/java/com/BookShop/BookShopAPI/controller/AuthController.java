package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.entity.JWTToken;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class AuthController {

    private final TokenBlacklistService service;

    public AuthController(TokenBlacklistService service) {
        this.service = service;
    }

    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        JWTToken jwtToken = new JWTToken(request.getHeader("Authorization"));
        if (!service.isTokenBlacklisted(jwtToken)){
            service.blacklistToken(jwtToken);
            return "Logged out successfully";
        }else{
            throw new BadRequestException("You have to login first to logout!");
        }
    }
}
