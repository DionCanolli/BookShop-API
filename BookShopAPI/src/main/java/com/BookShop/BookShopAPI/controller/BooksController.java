package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.entity.Books;
import com.BookShop.BookShopAPI.entity.Genres;
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
@RequestMapping(value = "/v1")
public class BooksController {

    private final APIService apiService;

    public BooksController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/book")
    public ResponseEntity<Books> insertBook(@RequestBody Books book) {
        Books bookInserted = apiService.insertBook(book);
        if(bookInserted != null)
            return new ResponseEntity<>(bookInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert Book");
    }

    @PostMapping(value = "/books")
    public ResponseEntity<List<Books>> insertBooks(@RequestBody List<Books> books){
        List<Books> allBooks = apiService.findAllBooks();
        Books bookExists = null;

        books.forEach(b -> allBooks.forEach(book -> {
            if (b.getTitle().equals(book.getTitle()))
                throw new BadRequestException("There is a/some book/s that exist");
        }));

        List<Books> booksInserted = apiService.insertBooks(books);

        if(!booksInserted.isEmpty())
            return new ResponseEntity<>(booksInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert Books");
    }

    @GetMapping(value = "/book/{bookId}")
    public ResponseEntity<Books> findBookById(@PathVariable String bookId){
        Books book = apiService.findBookById(bookId);
        if(book != null)
            return new ResponseEntity<>(book, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Book with id=" + bookId);
    }

    @GetMapping(value = "/book/title/{title}")
    public ResponseEntity<Books> findBookByTitle(@PathVariable String title){
        Books book = apiService.findBookByTitle(title);
        if(book != null)
            return new ResponseEntity<>(book, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Book with title=" + title);
    }

    @GetMapping(value = "/book/price/{price}")
    public ResponseEntity<List<Books>> findBooksByPrice(@PathVariable double price, @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }


        Page<Books> books = apiService.findBooksByPrice(price, pageable);

        if(!books.getContent().isEmpty())
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Book with price=" + price);
    }

    @GetMapping(value = "/book/price/greater/{price}")
    public ResponseEntity<List<Books>> findBooksGreaterThanPrice(@PathVariable double price, @RequestParam(required = false) Integer size,
                                                                 @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Books> books = apiService.findBooksGreaterThanPrice(price, pageable);

        if(!books.getContent().isEmpty())
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Book with price > " + price);
    }

    @GetMapping(value = "/book/price/less/{price}")
    public ResponseEntity<List<Books>> findBooksLessThanPrice(@PathVariable double price, @RequestParam(required = false) Integer size,
                                                              @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Books> books = apiService.findBooksLessThanPrice(price, pageable);

        if(!books.getContent().isEmpty())
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Book with price < " + price);
    }

    @GetMapping(value = "/book/genre/id")
    public ResponseEntity<List<Books>> findBooksByGenreId(@RequestParam String genreId, @RequestParam(required = false) Integer size,
                                                          @RequestParam(required = false) Integer pageNumber) {

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Books> books = apiService.findBooksByGenreId(genreId, pageable);

        if(books.getContent().size() == 0)
            throw new NotFoundException("Couldn't find Book with genreId = " + genreId);
        else
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
    }

    @GetMapping(value = "/book/genre/name")
    public ResponseEntity<List<Books>> findBooksByGenreName(@RequestParam String genreName, @RequestParam(required = false) Integer size,
                                                          @RequestParam(required = false) Integer pageNumber) {

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Genres genre;
        try{
            genre = apiService.findGenreByName(genreName);
        }catch (Exception e){
            throw new NotFoundException("Couldn't find Genre with name = " + genreName);
        }

        Page<Books> books = apiService.findBooksByGenreId(genre.getGenreId(), pageable);

        if(books.getContent().size() == 0)
            throw new NotFoundException("Couldn't find Book with genreName = " + genreName);
        else
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
    }

    @GetMapping(value = "/books")
    public ResponseEntity<List<Books>> findAllBooks(@RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false) Integer pageNumber) {

        Pageable pageable = PageRequest.of(0, 5);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Books> books = apiService.findAllBooks(pageable);

        if(books.getContent().size() == 0)
            throw new NotFoundException("Couldn't find any Book");
        else
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
    }

    @GetMapping(value = "/books/language/{language}")
    public ResponseEntity<List<Books>> findBooksByLanguage(@PathVariable String language,
                                                           @RequestParam(required = false) Integer size,
                                                           @RequestParam(required = false) Integer pageNumber) {

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Books> books = apiService.findBooksByLanguage(language, pageable);

        if(books.getContent().size() == 0)
            throw new NotFoundException("Couldn't find any Book with language=" + language);
        else
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
    }

    @PutMapping(value = "/book/id/{bookId}")
    public ResponseEntity<Books> updateBookById(@PathVariable String bookId, @RequestBody Books book) {
        Books existingBook = apiService.findBookById(bookId);

        if(existingBook != null) {
            if(book.getTitle() != null) existingBook.setTitle(book.getTitle());
            if(book.getPrice() > 0.0) existingBook.setPrice(book.getPrice());
            if(book.getDescription() != null) existingBook.setDescription(book.getDescription());
            if(book.getPageCount() > 0) existingBook.setPageCount(book.getPageCount());
            if(book.getLanguage() != null) existingBook.setLanguage(book.getLanguage());
            if(book.getGenreId() != null) existingBook.setGenreId(book.getGenreId());

            Books bookUpdated = apiService.updateBook(existingBook);
            return new ResponseEntity<>(bookUpdated, HttpStatus.OK);
        }
        else
            throw new BadRequestException("Couldn't update Book");
    }

    @PutMapping(value = "/book/title/{title}")
    public ResponseEntity<Books> updateBookByTitle(@PathVariable String title, @RequestBody Books book) {
        Books existingBook = apiService.findBookByTitle(title);

        if(existingBook != null) {

            if(book.getTitle() != null) existingBook.setTitle(book.getTitle());
            if(book.getPrice() > 0.0) existingBook.setPrice(book.getPrice());
            if(book.getDescription() != null) existingBook.setDescription(book.getDescription());
            if(book.getPageCount() > 0) existingBook.setPageCount(book.getPageCount());
            if(book.getLanguage() != null) existingBook.setLanguage(book.getLanguage());
            if(book.getGenreId() != null) existingBook.setGenreId(book.getGenreId());

            Books bookUpdated = apiService.updateBook(existingBook);
            return new ResponseEntity<>(bookUpdated, HttpStatus.OK);
        }
        else
            throw new BadRequestException("Couldn't update Book");
    }

    @DeleteMapping(value = "/book/title/{title}")
    public ResponseEntity<String> deleteBookByTitle(@PathVariable String title) {
        try{
            if (apiService.findBookByTitle(title) == null) {
                throw new NotFoundException("Couldn't delete Book");
            }
            apiService.deleteBookByTitle(title);
            return new ResponseEntity<>("Successfully deleted book: " + title, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("Couldn't delete Book");
        }
    }

    @GetMapping(value = "/books/stock")
    public ResponseEntity<List<Books>> findBooksThatHaveStock(@RequestParam(required = false) Integer size,
                                                              @RequestParam(required = false) Integer pageNumber) {

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Books> books = apiService.findBooksThatHaveStock(pageable);

        if(books.getContent().size() == 0)
            throw new NotFoundException("Couldn't find any Book");
        else
            return new ResponseEntity<>(books.getContent(), HttpStatus.OK);
    }


}
