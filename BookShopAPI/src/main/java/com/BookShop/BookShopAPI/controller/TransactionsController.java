package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.entity.Transactions;
import com.BookShop.BookShopAPI.entity.Users;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.exception.NotFoundException;
import com.BookShop.BookShopAPI.service.APIService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class TransactionsController {

    private final APIService apiService;

    public TransactionsController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/transaction")
    public ResponseEntity<Transactions> insertTransaction(@RequestBody Transactions transaction){
        Users user = apiService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        transaction.setUserID(user.getUserId());


        Transactions transactionInserted = apiService.insertTransaction(transaction);
        if (transactionInserted != null)
            return new ResponseEntity<>(transactionInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert transaction");
    }

    @GetMapping(value = "/transactions/user")
    public ResponseEntity<List<Transactions>> findTransactionsByUserId(@RequestParam String userID,
                                                                       @RequestParam(required = false) Integer size,
                                                                       @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionsByUserId(userID, pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @GetMapping(value = "/transactions/user/this")
    public ResponseEntity<List<Transactions>> findMyTransactions(@RequestParam(required = false) Integer size,
                                                                       @RequestParam(required = false) Integer pageNumber){
        Users user = apiService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionsByUserId(user.getUserId(), pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @GetMapping(value = "/transactions/book")
    public ResponseEntity<List<Transactions>> findTransactionsByBookId(@RequestParam String bookID,
                                                                       @RequestParam(required = false) Integer size,
                                                                       @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionsByBookId(bookID, pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @GetMapping(value = "/transactions")
    public ResponseEntity<List<Transactions>> findTransactionsByUserIdAndBookId(
                                                        @RequestParam String userID, @RequestParam String bookID,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionsByUserIdAndBookId(userID, bookID, pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @GetMapping(value = "/transactions/date")
    public ResponseEntity<List<Transactions>> findTransactionsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                     @RequestParam(required = false) Integer size,
                                                                     @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionByDate(date, pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @GetMapping(value = "/transactions/month")
    public ResponseEntity<List<Transactions>> findTransactionsByMonth(@RequestParam int month,
                                                                     @RequestParam(required = false) Integer size,
                                                                     @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionByMonth(month, pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @GetMapping(value = "/transactions/year")
    public ResponseEntity<List<Transactions>> findTransactionsByYear(@RequestParam int year,
                                                                     @RequestParam(required = false) Integer size,
                                                                     @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionByYear(year, pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @GetMapping(value = "/transactions/price")
    public ResponseEntity<List<Transactions>> findTransactionsByTotalPrice(@RequestParam double totalPrice,
                                                                     @RequestParam(required = false) Integer size,
                                                                     @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Transactions> transactions = apiService.findTransactionsByTotalPrice(totalPrice, pageable);

        if (!transactions.getContent().isEmpty())
            return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Transaction with given input!");
    }

    @DeleteMapping(value = "/transaction/user")
    public ResponseEntity<String> deleteTransactionByUserId(@RequestParam String userID){
            List<Transactions> transactions = apiService.findTransactionsByUserId(userID);
            if (!transactions.isEmpty()) {
                transactions.forEach(apiService::deleteTransaction);
                return new ResponseEntity<>("Successfully deleted all transactions for user: " +
                        apiService.findUserById(userID).getEmail(), HttpStatus.OK);
            }else
                throw new NotFoundException("Couldn't delete transaction with given input!");
    }
}
