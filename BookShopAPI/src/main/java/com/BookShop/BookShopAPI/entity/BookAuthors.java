package com.BookShop.BookShopAPI.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookAuthors")
public class BookAuthors {
    @Id
    private String bookAuthorId;
    private String authorId;
    private String bookId;

    public BookAuthors(String bookAuthorId, String authorId, String bookId) {
        this.bookAuthorId = bookAuthorId;
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public BookAuthors(String authorId, String bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public BookAuthors() {
    }

    public String getBookAuthorId() {
        return bookAuthorId;
    }

    public void setBookAuthorId(String book_AuthorId) {
        this.bookAuthorId = book_AuthorId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
