package com.BookShop.BookShopAPI.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "users")
@Builder
public class Users {

    @Id
    private String userId;
    private String username;
    private String email;
    private String password;
    private String roleId;

    public Users(String userId, String username, String email, String password, String roleId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public Users(String username, String email, String password, String roleId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public Users() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
