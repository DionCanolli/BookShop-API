package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.entity.Books;
import com.BookShop.BookShopAPI.entity.Roles;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.exception.NotFoundException;
import com.BookShop.BookShopAPI.service.APIService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RolesController {

    private APIService apiService;

    public RolesController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/admin/role")
    public ResponseEntity<Roles> insertRole(@RequestBody Roles role){
        Roles roleInserted = apiService.insertRole(role);
        if (roleInserted != null)
            return new ResponseEntity<>(roleInserted, HttpStatus.CREATED);
        else
            throw new NotFoundException("Couldn't insert the Role");
    }

    @PostMapping(value = "/admin/roles")
    public ResponseEntity<List<Roles>> insertRole(@RequestBody List<Roles> roles){
        List<Roles> rolesThatExist = apiService.findAllRoles();
        roles.forEach(role -> rolesThatExist.forEach(r -> {
            if (role.getRoleName().equals(r.getRoleName())){
                throw new NotFoundException("There must be one/many roles that exist");
            }
        }));

        List<Roles> rolesInserted = apiService.insertRoles(roles);

        if (!rolesInserted.isEmpty())
            return new ResponseEntity<>(rolesInserted, HttpStatus.CREATED);
        else
            throw new NotFoundException("Couldn't insert Roles");
    }

    @GetMapping(value = "/admin/role/id")
    public ResponseEntity<Roles> findRoleById(@RequestParam String roleId){
        Roles roleFound = apiService.findRoleById(roleId);
        if (roleFound != null)
            return new ResponseEntity<>(roleFound, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Role by id = " + roleId);
    }

    @GetMapping(value = "/admin/role")
    public ResponseEntity<Roles> findRoleByName(@RequestParam String roleName){
        Roles roleFound = apiService.findRoleByName(roleName);
        if (roleFound != null)
            return new ResponseEntity<>(roleFound, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Role by roleName = " + roleName);
    }

    @GetMapping(value = "/admin/roles")
    public ResponseEntity<List<Roles>> findAllRoles(@RequestParam(required = false) Integer size,
                                                      @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 5);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Roles> rolesFound = apiService.findAllRoles(pageable);
        if (!rolesFound.getContent().isEmpty())
            return new ResponseEntity<>(rolesFound.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Role");
    }

    @DeleteMapping(value = "/admin/role/id/{roleId}")
    public ResponseEntity<String> deleteRoleById(@PathVariable String roleId) {
        try{
            if (apiService.findRoleById(roleId) == null) {
                throw new NotFoundException("Couldn't delete Role");
            }
            apiService.deleteRoleById(roleId);
            return new ResponseEntity<>("Successfully deleted role with id: " + roleId, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("Couldn't delete Role");
        }
    }

    @DeleteMapping(value = "/admin/role/name/{roleName}")
    public ResponseEntity<String> deleteRoleByName(@PathVariable String roleName) {
        try{
            if (apiService.findRoleByName(roleName) == null) {
                throw new NotFoundException("Couldn't delete Role");
            }
            apiService.deleteRoleByName(roleName);
            return new ResponseEntity<>("Successfully deleted role with name: " + roleName, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("Couldn't delete Role");
        }
    }
}



















