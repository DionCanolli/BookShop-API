package com.BookShop.BookShopAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // mi paraqit si json veq qito property qe jan not null
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String roleId;
}
