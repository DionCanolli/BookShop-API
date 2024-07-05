package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.Authors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorsRepository extends MongoRepository<Authors, String> {

    @Query(value = "{ 'firstName': ?0, 'lastName': ?1 }")
    public Page<Authors> findAuthorByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    @Query(value = "{ 'authorEmail': ?0 }")
    public Authors findAuthorByAuthorEmail(String authorEmail);

    @Query(value = "{ 'nationality': ?0 }")
    public Page<Authors> findAuthorByNationality(String nationality, Pageable pageable);

    @Query(value = "{ 'nationality': ?0 }")
    public List<Authors> findAuthorByNationality(String nationality);
}
