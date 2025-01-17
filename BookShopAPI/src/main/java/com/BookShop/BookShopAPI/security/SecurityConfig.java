//package com.BookShop.BookShopAPI.security;
//
//import com.BookShop.BookShopAPI.filter.*;
//import com.BookShop.BookShopAPI.filter.JWTValidatorFilter;
//import com.BookShop.BookShopAPI.service.APIService;
//import com.BookShop.BookShopAPI.service.TokenBlacklistService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Lazy
//    @Autowired
//    private TokenBlacklistService tokenBlacklistService;
//
//    @Autowired
//    private APIService apiService;
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Lazy
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Bean // post /v1/review, post /v1/transactions,
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                    .authorizeHttpRequests((requests) ->
//                                    requests
////                                        .requestMatchers(HttpMethod.POST, "/v1/book", "/v1/books",
////                                                "/v1/genre", "/v1/genres", "/v1/author", "/v1/authors",
////                                                "/v1/bookAuthor", "/v1/bookAuthors", "/v1/role", "/v1/roles")
////                                            .hasRole("ADMIN")
////                                        .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
////                                        .requestMatchers(HttpMethod.DELETE, "/v1/book/title/**", "/v1/genre/name/**",
////                                                "/v1/author", "/v1/bookAuthor/id/**", "/v1/role/name/**", "/v1/role/id/**",
////                                                "/v1/user", "/v1/transaction/user", "/v1/review/user").hasRole("ADMIN")
////                                        .requestMatchers(HttpMethod.GET, "/v1/role", "/v1/roles/**",
////                                                "/v1/user", "/v1/user/email", "/v1/transactions/user", "/v1/transactions/book",
////                                                "/v1/transactions", "/v1/transactions/date", "/v1/transactions/month",
////                                                "/v1/transactions/year", "/v1/transactions/price").hasRole("ADMIN")
//                                        .requestMatchers("/v1/signup", "/v1/login").permitAll()
//                                        .anyRequest().authenticated()
//                    )
//                    .csrf(AbstractHttpConfigurer::disable)
//                    .userDetailsService(customUserDetailsService)
//        /* 2: */        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        /* 3: */    .cors(configurer -> {
//                        CorsConfiguration corsConfig = new CorsConfiguration();
//                        corsConfig.setExposedHeaders(Arrays.asList("Authorization"));
//                        configurer.configurationSource(request -> corsConfig);
//                    })
//                    .csrf(AbstractHttpConfigurer::disable)
//        /* 8.1: */    .addFilterAfter(new JWTGeneratorFilter(), BasicAuthenticationFilter.class)
////        /* 8.2: */    .addFilterAfter(new NewJWTGeneratorFilter(), BasicAuthenticationFilter.class)
//        /* 10: */   .addFilterBefore(new CustomUsernamePasswordAuthenticationFilter(authenticationManager, apiService),
//                            UsernamePasswordAuthenticationFilter.class)
//        /* 6.1: */    .addFilterAfter(new JWTValidatorFilter(tokenBlacklistService), BasicAuthenticationFilter.class)
////        /* 6.2: */    .addFilterAfter(new NewJWTValidatorFilter(tokenBlacklistService), JWTGeneratorFilter.class)
//                    .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new SCryptPasswordEncoder(16384, 8, 1, 32, 16);
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder a){
//        return a.getObject();
//    }
//}

package com.BookShop.BookShopAPI.security;

import com.BookShop.BookShopAPI.filter.CustomUsernamePasswordAuthenticationFilter;
import com.BookShop.BookShopAPI.filter.JWTValidatorFilter;
import com.BookShop.BookShopAPI.service.APIService;
import com.BookShop.BookShopAPI.service.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private APIService myService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers("/admin/**")
                                .hasRole("ADMIN")
                                .requestMatchers("/permitted/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(customUserDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager, myService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JWTValidatorFilter(tokenBlacklistService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder a){
        return a.getObject();
    }
}
