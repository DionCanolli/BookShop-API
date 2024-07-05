package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.JWTToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTRepository extends MongoRepository<JWTToken, String> {

    @Query(value = "{ 'value': ?0 }")
    public JWTToken findJWTTokenByValue(String value);
}
