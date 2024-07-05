package com.BookShop.BookShopAPI.entity;

import com.BookShop.BookShopAPI.enums.RoleEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Roles {
    @Id
    private String roleId;
    @Indexed(unique = true)
    private RoleEnum roleName;

    public Roles(String roleId, RoleEnum roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Roles(RoleEnum roleName) {
        this.roleName = roleName;
    }

    public Roles() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public RoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleEnum roleName) {
        this.roleName = roleName;
    }
}
