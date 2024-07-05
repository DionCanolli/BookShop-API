package com.BookShop.BookShopAPI.mapper;

import com.BookShop.BookShopAPI.dto.UserDTO;
import com.BookShop.BookShopAPI.entity.Users;
import com.BookShop.BookShopAPI.enums.RoleEnum;
import org.apache.catalina.User;

public class Mapper {

    public static Users mapToUser(UserDTO userDTO){
       return Users.builder()
               .username(userDTO.getUsername())
               .email(userDTO.getEmail())
               .password(userDTO.getPassword())
               .roleId(userDTO.getRoleId())
               .build();
    }

    public static UserDTO mapToUserDTO(Users user){
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleId(user.getRoleId())
                .build();
    }
}
