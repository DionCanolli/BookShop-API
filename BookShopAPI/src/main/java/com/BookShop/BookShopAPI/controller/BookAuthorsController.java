package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.entity.Authors;
import com.BookShop.BookShopAPI.entity.BookAuthors;
import com.BookShop.BookShopAPI.entity.Books;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.exception.NotFoundException;
import com.BookShop.BookShopAPI.service.APIService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookAuthorsController {

    private final APIService apiService;

    public BookAuthorsController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/admin/bookAuthor")
    public ResponseEntity<BookAuthors> insertBookAuthor(@RequestBody BookAuthors bookAuthor){
        BookAuthors bookAuthorInserted = apiService.insertBookAuthor(bookAuthor);
        if (bookAuthorInserted != null)
            return new ResponseEntity<>(bookAuthorInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert BookAuthor");
    }

    // Me kqyr a egziston najnje, por edhe mos ka naj null ne authorId apo bookId
    @PostMapping(value = "/admin/bookAuthors")
    public ResponseEntity<List<BookAuthors>> insertBookAuthors(@RequestBody List<BookAuthors> bookAuthors){

        List<BookAuthors> bookAuthorsThatExist = apiService.findAllBookAuthors();

        bookAuthors.forEach(bkAth ->
                bookAuthorsThatExist.forEach(ba -> {
                    if (bkAth.getAuthorId() == null || bkAth.getBookId() == null
                    || (ba.getAuthorId().equals(bkAth.getAuthorId()) && ba.getBookId().equals(bkAth.getBookId()))
                    || apiService.findBookById(bkAth.getBookId()) == null || apiService.findAuthorById(bkAth.getAuthorId()) == null)
                        throw new BadRequestException("Couldn't insert BookAuthor");
        }));

        List<BookAuthors> bookAuthorInserted = apiService.insertBookAuthors(bookAuthors);
        if (!bookAuthorInserted.isEmpty())
            return new ResponseEntity<>(bookAuthorInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert BookAuthor");
    }

    @GetMapping(value = "/bookAuthor/author/id")
    public ResponseEntity<List<BookAuthors>> findBookAuthorByAuthorId(@RequestParam String authorId,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 5);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Authors author = apiService.findAuthorById(authorId);
        Page<BookAuthors> bookAuthors = apiService.findBookAuthorsByAuthorId(authorId, pageable);

        if (!bookAuthors.isEmpty() && author != null)
            return new ResponseEntity<>(bookAuthors.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find BookAuthor by authorId=" + authorId);
    }

    @GetMapping(value = "/bookAuthor/book/id")
    public ResponseEntity<List<BookAuthors>> findBookAuthorByBookId(@RequestParam String bookId,
                                                                    @RequestParam(required = false) Integer size,
                                                                    @RequestParam(required = false) Integer pageNumber){
        Pageable pageable = PageRequest.of(0, 5);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Books book = apiService.findBookById(bookId);
        Page<BookAuthors> bookAuthors = apiService.findBookAuthorsByBookId(bookId, pageable);

        if (!bookAuthors.isEmpty() && book != null)
            return new ResponseEntity<>(bookAuthors.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any BookAuthor by bookId=" + bookId);
    }

    @GetMapping(value = "/bookAuthor/author/email")
    public ResponseEntity<List<BookAuthors>> findBookAuthorByAuthorEmail(@RequestParam String authorEmail){

        List<BookAuthors> bookAuthors = apiService.findBookAuthorsByAuthorEmail(authorEmail);

        if (!bookAuthors.isEmpty())
            return new ResponseEntity<>(bookAuthors, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find BookAuthor by author email = " + authorEmail);
    }

    @GetMapping(value = "/bookAuthor/book/title")
    public ResponseEntity<List<BookAuthors>> findBookAuthorsByBookTitle(@RequestParam String title){

        List<BookAuthors> bookAuthors = apiService.findBookAuthorsByBookTitle(title);

        if (!bookAuthors.isEmpty())
            return new ResponseEntity<>(bookAuthors, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find BookAuthor by book title = " + title);
    }

    @GetMapping(value = "/bookAuthor")
    public ResponseEntity<BookAuthors> findBookAuthorsByAuthorIdAndBookId(@RequestParam String authorId,
                                                                          @RequestParam String bookId){

        BookAuthors bookAuthor = apiService.findBookAuthorByAuthorIdAndBookId(authorId, bookId);

        if (bookAuthor != null)
            return new ResponseEntity<>(bookAuthor, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find BookAuthor by given author id and book id");
    }

    @PutMapping(value = "/admin/bookAuthor/id/{bookAuthorId}")
    public ResponseEntity<BookAuthors> updateBookById(@PathVariable String bookAuthorId, @RequestBody BookAuthors bookAuthor) {
        BookAuthors existingBookAuthor = apiService.findBookAuthorByBookAuthorId(bookAuthorId);

        if(existingBookAuthor != null) {
            if(bookAuthor.getAuthorId() != null) existingBookAuthor.setAuthorId(bookAuthor.getAuthorId());
            if(bookAuthor.getBookId() != null) existingBookAuthor.setBookId(bookAuthor.getBookId());

            BookAuthors bookAuthorUpdated = apiService.updateBookAuthor(existingBookAuthor);

            return new ResponseEntity<>(bookAuthorUpdated, HttpStatus.OK);
        }
        else
            throw new BadRequestException("Couldn't update BookAuthor");
    }

    @DeleteMapping(value = "/admin/bookAuthor/id/{bookAuthorId}")
    public ResponseEntity<String> deleteBookByTitle(@PathVariable String bookAuthorId) {
        try{
            if (apiService.findBookAuthorByBookAuthorId(bookAuthorId) == null) {
                throw new NotFoundException("BookAuthor doesn't exist");
            }
            apiService.deleteBookAuthorById(bookAuthorId);
            return new ResponseEntity<>("Successfully deleted BookAuthor: " + bookAuthorId, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("Couldn't delete Book");
        }
    }
}
