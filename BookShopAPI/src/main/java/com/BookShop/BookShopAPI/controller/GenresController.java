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
public class GenresController {

    private final APIService apiService;

    public GenresController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/genre")
    public ResponseEntity<Genres> insertGenre(@RequestBody Genres genres){
        Genres genreInserted = apiService.insertGenre(genres);
        if(genreInserted != null)
            return new ResponseEntity<>(genreInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert Genre");
    }

    @PostMapping(value = "/genres")
    public ResponseEntity<List<Genres>> insertGenres(@RequestBody List<Genres> genres){
        List<Genres> genresInserted = apiService.insertGenres(genres);
        if(!genresInserted.isEmpty())
            return new ResponseEntity<>(genresInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert Genre");
    }

    @GetMapping(value = "/genre/id/{genreId}")
    public ResponseEntity<Genres> findGenreById(@PathVariable String genreId){

        Genres genre = apiService.findGenreById(genreId);

        if(genre != null)
            return new ResponseEntity<>(genre, HttpStatus.OK);
        else
            throw new BadRequestException("Couldn't find Genre with id = " + genreId);
    }

    @GetMapping(value = "/genre/name/{genreName}")
    public ResponseEntity<Genres> findGenreByName(@PathVariable String genreName){

        Genres genre = apiService.findGenreByName(genreName);

        if(genre != null)
            return new ResponseEntity<>(genre, HttpStatus.OK);
        else
            throw new BadRequestException("Couldn't find Genre with name = " + genreName);
    }

    @PutMapping(value = "/genre/name/{name}")
    public ResponseEntity<Genres> updateGenreByName(@PathVariable String name, @RequestBody Genres genre) {
        Genres existingGenre = apiService.findGenreByName(name);

        if(existingGenre != null) {
            if(genre.getName() != null) existingGenre.setName(genre.getName());

            Genres genreUpdated = apiService.updateGenre(existingGenre);
            return new ResponseEntity<>(genreUpdated, HttpStatus.OK);
        }
        else
            throw new BadRequestException("Couldn't update Genre");
    }

    // Ktu duhet fillimisht mja lan cdo libri qe e ka kete genre (genreId-n = id e kesaj genre qe po dojna me fshi) ne null,
    // e pastaj me fshi.
    @DeleteMapping(value = "/genre/name/{name}")
    public ResponseEntity<String> deleteBookByTitle(@PathVariable String name) {
        try{
            Genres genre = apiService.findGenreByName(name);
            try{
                List<Books> booksWithThisGenre = apiService.findBooksByGenreId(genre.getGenreId());
                booksWithThisGenre.forEach(book -> {
                    book.setGenreId(null);
                    apiService.updateBook(book);
                });
            }catch (Exception ex){
                throw new BadRequestException(ex.getMessage());
            }
            apiService.deleteGenreByName(name);
            return new ResponseEntity<>("Successfully deleted genre: " + name, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("Couldn't delete genre");
        }
    }

    @GetMapping(value = "/genres")
    public ResponseEntity<List<Genres>> findAllGenres(@RequestParam(required = false) Integer size,
                                                      @RequestParam(required = false) Integer pageNumber) {

        Pageable pageable = PageRequest.of(0, 5);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Genres> genres = apiService.findAllGenres(pageable);

        if(genres.getContent().size() == 0)
            throw new NotFoundException("Couldn't find any Genre");
        else
            return new ResponseEntity<>(genres.getContent(), HttpStatus.OK);
    }
}
