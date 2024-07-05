package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends MongoRepository<Genres, String> {

    @Query("{ 'name': ?0 }")
    Genres findGenreByName(String genreName);
}
