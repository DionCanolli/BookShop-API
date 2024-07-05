package com.BookShop.BookShopAPI.entity;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
public class Transactions {
    @Id
    private String transactionId;
    @NotEmpty
    private String userID;
    @NotEmpty
    private String bookID;
    @NotEmpty
    private int stock;
    private double totalPrice;
    @CreatedDate
    private LocalDateTime dateTimeOfTransaction;

    public Transactions(String bookID, int stock) {
        this.bookID = bookID;
        this.stock = stock;
    }

    public Transactions(String userID, String bookID, int stock, double totalPrice) {
        this.userID = userID;
        this.bookID = bookID;
        this.stock = stock;
        this.totalPrice = totalPrice;
    }

    public Transactions(String transactionId, String userID, String bookID, int stock, double totalPrice) {
        this.transactionId = transactionId;
        this.userID = userID;
        this.bookID = bookID;
        this.stock = stock;
        this.totalPrice = totalPrice;
    }

    public Transactions() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDateTimeOfTransaction() {
        return dateTimeOfTransaction;
    }

    public void setDateTimeOfTransaction(LocalDateTime dateTimeOfTransaction) {
        this.dateTimeOfTransaction = dateTimeOfTransaction;
    }
}
