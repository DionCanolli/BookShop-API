package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.BookAuthors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorsRepository extends MongoRepository<BookAuthors, String> {

    @Query(value = "{'authorId': ?0, 'bookId': ?1}")
    public BookAuthors findBookAuthorByAuthorIdAndBookId(String authorId, String bookId);

    @Query(value = "{ 'authorId': ?0 }")
    public Page<BookAuthors> findBookAuthorsByAuthorId(String authorId, Pageable pageable);

    @Query(value = "{ 'bookId': ?0 }")
    public Page<BookAuthors> findBookAuthorsByBookId(String bookId, Pageable pageable);

    @Query(value = "{ 'authorId': ?0 }")
    public List<BookAuthors> findBookAuthorsByAuthorId(String authorId);

    @Query(value = "{ 'bookId': ?0 }")
    public List<BookAuthors> findBookAuthorsByBookId(String bookId);
}
