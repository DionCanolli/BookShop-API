package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.dto.UserDTO;
import com.BookShop.BookShopAPI.entity.JWTToken;
import com.BookShop.BookShopAPI.entity.Users;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.exception.NotFoundException;
import com.BookShop.BookShopAPI.mapper.Mapper;
import com.BookShop.BookShopAPI.service.APIService;
import com.BookShop.BookShopAPI.service.TokenBlacklistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsersController {

    private APIService apiService;
    private TokenBlacklistService tokenBlacklistService;

    public UsersController(APIService apiService, TokenBlacklistService tokenBlacklistService) {
        this.apiService = apiService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping(value = "/permitted/signup")
    public ResponseEntity<UserDTO> insertUser(@RequestBody UserDTO userDTO){

        if (apiService.findUserByEmail(userDTO.getEmail()) != null)
            throw new BadRequestException("User with email = " + userDTO.getEmail() + " exists!");

        if(apiService.findRoleById(userDTO.getRoleId()) == null)
            throw new NotFoundException("Role doesn't exist!");

        if(userDTO.getUsername() == null || userDTO.getEmail() == null || userDTO.getPassword() == null ||
                !userDTO.getPassword().equals(userDTO.getConfirmPassword()))
            throw new BadRequestException("Please fill all fields with correct data!");

        Users user = Mapper.mapToUser(userDTO);

        Users insertedUser = apiService.insertUser(user);

        if (insertedUser == null)
            throw new BadRequestException("Couldn't insert User");

        UserDTO insertedUserDTO = UserDTO.builder()
                                         .username(user.getUsername())
                                         .email(user.getEmail())
                                         .build();

        return new ResponseEntity<>(insertedUserDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/admin/user/email")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email){
        Users user = apiService.findUserByEmail(email);

        if(user != null)
            return new ResponseEntity<>(
                    UserDTO.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build(),
                    HttpStatus.OK);
        else
            throw new NotFoundException("Not found user = " + email);
    }

    @GetMapping(value = "/admin/user")
    public ResponseEntity<List<UserDTO>> findAllUsers(@RequestParam(required = false) Integer size,
                                                       @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        List<UserDTO> userDTOS = new ArrayList<>();
        Page<Users> users = apiService.findAllUsers(pageable);

        if(!users.getContent().isEmpty()) {

            users.getContent().forEach(u -> userDTOS.add(
                    UserDTO.builder()
                           .username(u.getUsername())
                           .email(u.getEmail())
                           .build()));

            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        }
        else
            throw new NotFoundException("Not found any user");
    }

    @DeleteMapping(value = "/admin/user")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email){
        try{
            apiService.deleteUserByEmail(email);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        }catch (Exception ex){
            throw new BadRequestException("Couldn't delete user = " + email);
        }
    }

    @DeleteMapping(value = "/user/mine")
    public String deleteMyUser(){
        Users user = apiService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        try{
            apiService.deleteUserByEmail(user.getEmail());
            return "redirect:/logout";
        }catch (Exception ex){
            throw new BadRequestException("Couldn't delete user = " + user.getEmail());
        }
    }

    @PostMapping(value = "/users/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader(value = "Authorization") String jwtToken){
        try{
            tokenBlacklistService.blacklistToken(new JWTToken(jwtToken));
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}



















