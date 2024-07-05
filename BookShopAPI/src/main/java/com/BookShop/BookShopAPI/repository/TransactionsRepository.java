package com.BookShop.BookShopAPI.repository;

import com.BookShop.BookShopAPI.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionsRepository extends MongoRepository<Transactions, String> {

    @Query(value = "{ 'userID': ?0 }")
    Page<Transactions> findTransactionsByUserId(String userId, Pageable pageable);

    @Query(value = "{ 'userID': ?0 }")
    List<Transactions> findTransactionsByUserId(String userId);

    @Query(value = "{ 'bookID': ?0 }")
    Page<Transactions> findTransactionsByBookId(String bookId, Pageable pageable);

    @Query(value = "{ 'bookID': ?0 }")
    List<Transactions> findTransactionsByBookId(String bookId);

    @Query(value = "{ 'userID': ?0, 'bookID': ?1 }")
    Page<Transactions> findTransactionsByUserIdAndBookId(String userId, String bookId, Pageable pageable);

    @Query(value = "{ 'totalPrice': ?0 }")
    Page<Transactions> findTransactionsByTotalPrice(double totalPrice, Pageable pageable);

    @Query("{ 'dateTimeOfTransaction': { '$gte': ?0, '$lt': ?1 } }")
    Page<Transactions> findByDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("{ '$expr': { '$eq': [{ '$month': '$dateTimeOfTransaction' }, ?0] } }")
    Page<Transactions> findByMonth(int month, Pageable pageable);

    @Query("{ '$expr': { '$eq': [{ '$year': '$dateTimeOfTransaction' }, ?0] } }")
    Page<Transactions> findByYear(int year, Pageable pageable);
}
