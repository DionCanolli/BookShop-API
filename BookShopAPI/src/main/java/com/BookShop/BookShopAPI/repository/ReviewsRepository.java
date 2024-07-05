package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends MongoRepository<Reviews, String> {

    @Query(value = "{ 'userId': ?0 }")
    Page<Reviews> findReviewsByUserId(String userId, Pageable pageable);

    @Query(value = "{ 'userId': ?0 }")
    List<Reviews> findReviewsByUserId(String userId);

    @Query(value = "{ 'bookId': ?0 }")
    Page<Reviews> findReviewsByBookId(String bookId, Pageable pageable);

    @Query(value = "{ 'rating': ?0 }")
    Page<Reviews> findReviewsByRating(double rating, Pageable pageable);

    @Query(value = "{ 'userId': ?0, 'bookId': ?1 }")
    Reviews findReviewByUserIdAndBookId(String userId, String bookId);
}
