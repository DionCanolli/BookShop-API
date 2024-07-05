package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {

    @Query(value = "{ 'email' : ?0 }")
    public Users findUserByEmail(String email);

}
