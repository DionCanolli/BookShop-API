package com.BookShop.BookShopAPI.entity;

import com.BookShop.BookShopAPI.enums.NationalityOrLanguage;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//@Builder
@Document(collection = "books")
public class Books {
    @Id
    private String bookId;
    private String title;
    private double price;
    private Date publificationDate;
    private String description;
    private int pageCount;
    private NationalityOrLanguage language;
    private String genreId;
    private int stock;

    public Books(String title, double price, Date publificationDate, String description, int pageCount, NationalityOrLanguage language, String genreId, int stock) {
        this.title = title;
        this.price = price;
        this.publificationDate = publificationDate;
        this.description = description;
        this.pageCount = pageCount;
        this.language = language;
        this.genreId = genreId;
        this.stock = stock;
    }

    public Books(String bookId, String title, double price, Date publificationDate, String description, int pageCount, NationalityOrLanguage language, String genreId) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.publificationDate = publificationDate;
        this.description = description;
        this.pageCount = pageCount;
        this.language = language;
        this.genreId = genreId;
    }

    public Books() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPublificationDate() {
        return publificationDate;
    }

    public void setPublificationDate(Date publificationDate) {
        this.publificationDate = publificationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public NationalityOrLanguage getLanguage() {
        return language;
    }

    public void setLanguage(NationalityOrLanguage language) {
        this.language = language;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
