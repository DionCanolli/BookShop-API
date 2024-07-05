package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.Books;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends MongoRepository<Books, String> {

    @Query(value = "{'title': ?0}")
    Books findBookByTitle(String title);

    @Query(value = "{'price': ?0}")
    Page<Books> findBooksByPrice(double price, Pageable pageable);

    @Query(value = "{'price': { $gt: ?0 }}")
    Page<Books> findBooksGreaterThanPrice(double price, Pageable pageable);

    @Query(value = "{'price': { $lt: ?0 }}")
    Page<Books> findBooksLessThanPrice(double price, Pageable pageable);

    @Query(value = "{'genreId': ?0}")
    Page<Books> findBooksByGenreId(String genreId, Pageable pageable);

    @Query(value = "{'genreId': ?0}")
    List<Books> findBooksByGenreId(String genreId);

    @Query(value = "{'language' : ?0}")
    Page<Books> findBooksByLanguage(String language, Pageable pageable);

    @Query(value = "{ 'stock' : {$gt: 0} }")
    Page<Books> findBooksThatHaveStock(Pageable pageable);
}




















