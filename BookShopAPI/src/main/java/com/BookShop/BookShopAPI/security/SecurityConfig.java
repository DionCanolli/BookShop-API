package com.BookShop.BookShopAPI.security;

import com.BookShop.BookShopAPI.entity.Users;
import com.BookShop.BookShopAPI.filter.*;
import com.BookShop.BookShopAPI.filter.JWTGeneratorFilter;
import com.BookShop.BookShopAPI.filter.JWTValidatorFilter;
import com.BookShop.BookShopAPI.service.APIService;
import com.BookShop.BookShopAPI.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Lazy
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private APIService apiService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean // post /v1/review, post /v1/transactions,
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                    .authorizeHttpRequests((requests) ->
                                    requests
                                        .requestMatchers(HttpMethod.POST, "/v1/book", "/v1/books",
                                                "/v1/genre", "/v1/genres", "/v1/author", "/v1/authors",
                                                "/v1/bookAuthor", "/v1/bookAuthors", "/v1/role", "/v1/roles")
                                            .hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/v1/book/title/**", "/v1/genre/name/**",
                                                "/v1/author", "/v1/bookAuthor/id/**", "/v1/role/name/**", "/v1/role/id/**",
                                                "/v1/user", "/v1/transaction/user", "/v1/review/user").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/v1/role", "/v1/roles/**",
                                                "/v1/user", "/v1/user/email", "/v1/transactions/user", "/v1/transactions/book",
                                                "/v1/transactions", "/v1/transactions/date", "/v1/transactions/month",
                                                "/v1/transactions/year", "/v1/transactions/price").hasRole("ADMIN")
                                        .requestMatchers("/v1/signup", "/v1/login").permitAll()
                                        .anyRequest().authenticated()
                    )
                    .csrf(AbstractHttpConfigurer::disable)
                    .userDetailsService(customUserDetailsService)
        /* 2: */        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        /* 3: */    .cors(configurer -> {
                        CorsConfiguration corsConfig = new CorsConfiguration();
                        corsConfig.setExposedHeaders(Arrays.asList("Authorization"));
                        configurer.configurationSource(request -> corsConfig);
                    })
                    .csrf(AbstractHttpConfigurer::disable)
        /* 8: */    .addFilterBefore(new JWTGeneratorFilter(), BasicAuthenticationFilter.class)
        /* 10: */   .addFilterBefore(new CustomUsernamePasswordAuthenticationFilter(authenticationManager, apiService),
                            UsernamePasswordAuthenticationFilter.class)
        /* 6: */    .addFilterAfter(new JWTValidatorFilter(tokenBlacklistService), BasicAuthenticationFilter.class)
                    .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new SCryptPasswordEncoder(16384, 8, 1, 32, 16);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder a){
        return a.getObject();
    }
}
