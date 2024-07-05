package com.BookShop.BookShopAPI.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
public class Reviews {
    @Id
    private String reviewId;
    private String userId;
    private String bookId;
    private double rating;
    private String reviewText;
    @CreatedDate
    private LocalDateTime  reviewDate;

    public Reviews(String bookId, double rating, String reviewText) {
        this.bookId = bookId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public Reviews(String userId, String bookId, double rating, String reviewText) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public Reviews() {
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}
