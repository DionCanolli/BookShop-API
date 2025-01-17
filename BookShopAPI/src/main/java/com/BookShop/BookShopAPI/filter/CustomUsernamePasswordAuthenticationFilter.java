package com.BookShop.BookShopAPI.filter;

import com.BookShop.BookShopAPI.entity.Roles;
import com.BookShop.BookShopAPI.entity.Users;
import com.BookShop.BookShopAPI.service.APIService;
import com.BookShop.BookShopAPI.utility.JWTTokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 9:
//public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//    private final APIService apiService;
//
//    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, APIService apiService) {
//        this.authenticationManager = authenticationManager;
//        this.apiService = apiService;
//        setFilterProcessesUrl("/v1/login");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//
//        try {
//            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
//            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//
//            String email = credentials.get("email");
//            String password = credentials.get("password");
//
//            Users user = apiService.findUserByEmail(email);
//
//            Roles role = apiService.findRoleById(user.getRoleId());
//            authorityList.add(new SimpleGrantedAuthority(role.getRoleName().name()));
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(email, password, authorityList);
//
//
//            return authenticationManager.authenticate(authenticationToken);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException {
//        JWTTokenGenerator.generateJWTToken(authResult, response);
//    }
//}

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final APIService myService;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, APIService myService) {
        this.authenticationManager = authenticationManager;
        this.myService = myService;
        setFilterProcessesUrl("/permitted/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

            String email = credentials.get("email");
            String password = credentials.get("password");

            Users user = myService.findUserByEmail(email);

            if (user != null){
                Roles role = myService.findRoleById(user.getRoleId());
                authorityList.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password, authorityList);
                return authenticationManager.authenticate(authenticationToken);
            }
        } catch (IOException ignored) {}
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwtToken = JWTTokenGenerator.generateJWTToken(authResult, response);
        Map<String, String> responseBody = Map.of("token", jwtToken);

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }
}