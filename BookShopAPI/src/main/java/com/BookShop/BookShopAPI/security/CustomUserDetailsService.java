//package com.BookShop.BookShopAPI.security;
//
//import com.BookShop.BookShopAPI.entity.Roles;
//import com.BookShop.BookShopAPI.entity.Users;
//import com.BookShop.BookShopAPI.service.APIService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Slf4j
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private APIService apiService;
//
//
//    public CustomUserDetailsService(APIService apiService) {
//        this.apiService = apiService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = apiService.findUserByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        Roles role = apiService.findRoleById(user.getRoleId());
//        authorities.add(new SimpleGrantedAuthority(role.getRoleName().name()));
//
//        return new User(user.getEmail(), user.getPassword(), authorities);
//    }
//}

package com.BookShop.BookShopAPI.security;

import com.BookShop.BookShopAPI.entity.Roles;
import com.BookShop.BookShopAPI.entity.Users;
import com.BookShop.BookShopAPI.service.APIService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final APIService myService;

    public CustomUserDetailsService(APIService myService) {
        this.myService = myService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = myService.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Roles role = myService.findRoleById(user.getRoleId());
        authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
