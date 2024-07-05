package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String> {

    @Query(value = "{'roleName' : ?0}")
    Optional<Roles> findRoleByName(String roleName);
}
