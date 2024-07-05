package com.BookShop.BookShopAPI.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Slf4j
public class MyAspect {

    @Before(value = "execution(* com.BookShop.BookShopAPI.*.*(..))")
    public void executeBefore(JoinPoint joinPoint){
        log.info(" ---------------- Executing method: " + joinPoint.getSignature().getName() + " ------------------------");
    }

    @After(value = "execution(* com.BookShop.BookShopAPI.*.*(..))")
    public void executeAfter(JoinPoint joinPoint){
        log.info(" ---------------- Fnished executing method: " + joinPoint.getSignature().getName() + " ------------------------");
    }
}
