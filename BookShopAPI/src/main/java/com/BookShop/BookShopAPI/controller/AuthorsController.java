package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.entity.Authors;
import com.BookShop.BookShopAPI.entity.Books;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.exception.NotFoundException;
import com.BookShop.BookShopAPI.service.APIService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class AuthorsController {

    private final APIService apiService;

    public AuthorsController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/admin/author")
    public ResponseEntity<Authors> insertAuthor(@RequestBody Authors author){
        Authors authorInserted = apiService.insertAuthor(author);
        if (authorInserted != null)
            return new ResponseEntity<>(authorInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert Author");
    }

    @PostMapping(value = "/admin/authors")
    public ResponseEntity<List<Authors>> insertAuthor(@RequestBody List<Authors> authors){
        List<Authors> authorsExist = apiService.findAllAuthors();

        authors.forEach(a -> authorsExist.forEach(ae -> {
            if (a.getAuthorEmail().equals(ae.getAuthorEmail())){
                throw new BadRequestException("There's an author that exist.");
            }
        }));

        List<Authors> authorsInserted = apiService.insertAuthors(authors);

        if (!authorsInserted.isEmpty())
            return new ResponseEntity<>(authorsInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert Authors");
    }

    @GetMapping(value = "/author/{authorId}")
    public ResponseEntity<Authors> findAuthorById(@PathVariable String authorId){
        Authors author = apiService.findAuthorById(authorId);
        if (author != null)
            return new ResponseEntity<>(author, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Author with authorid = " + authorId);
    }

    @GetMapping(value = "/authors/firstNameAndLastName")
    public ResponseEntity<List<Authors>> findAuthorByFirstNameAndLastName(@RequestParam String firstName,
                                                                    @RequestParam String lastName,
                                                                    @RequestParam(required = false) Integer size,
                                                                    @RequestParam(required = false) Integer pageNumber){
        Pageable pageable = PageRequest.of(0, 2);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Authors> authors = apiService.findAuthorByFirstNameAndLastName(firstName, lastName, pageable);
        if (!authors.isEmpty())
            return new ResponseEntity<>(authors.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Author with given first name and last name");
    }

    @GetMapping(value = "/author/email")
    public ResponseEntity<Authors> findAuthorByEmail(@RequestParam String authorEmail, HttpServletRequest request){
        Authors author = apiService.findAuthorByAuthorEmail(authorEmail);
        if (author != null)
            return new ResponseEntity<>(author, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find Author with authorEmail = " + authorEmail);
    }

    @GetMapping(value = "/authors/nationality/{nationality}")
    public ResponseEntity<List<Authors>> findAuthorsByNationality(@PathVariable String nationality){
        List<Authors> authors = apiService.findAuthorByNationality(nationality);
        if (!authors.isEmpty())
            return new ResponseEntity<>(authors, HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Author with nationality = " + nationality);
    }

    @PutMapping(value = "/admin/author/email")
    public ResponseEntity<Authors> updateAuthorByEmail(@RequestParam String authorEmail, @RequestBody Authors author) {
        Authors existingAuthor = apiService.findAuthorByAuthorEmail(authorEmail);

        if(existingAuthor != null) {

            if(author.getFirstName() != null) existingAuthor.setFirstName(author.getFirstName());
            if(author.getLastName() != null) existingAuthor.setLastName(author.getLastName());
            if(author.getBio() != null) existingAuthor.setBio(author.getBio());
            if(author.getAuthorEmail() != null) existingAuthor.setAuthorEmail(author.getAuthorEmail());
            if(author.getDateOfBirth() != null) existingAuthor.setDateOfBirth(author.getDateOfBirth());
            if(author.getNationality() != null) existingAuthor.setNationality(author.getNationality());

            Authors authorUpdated = apiService.updateAuthor(existingAuthor);
            return new ResponseEntity<>(authorUpdated, HttpStatus.OK);
        }
        else
            throw new BadRequestException("Couldn't update Author");
    }

    @DeleteMapping(value = "/admin/author")
    public ResponseEntity<String> deleteAuthorByEmail(@RequestParam String authorEmail) {
        try{
            apiService.deleteAuthorByEmail(authorEmail);
            return new ResponseEntity<>("Successfully deleted author: " + authorEmail, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("Couldn't delete Author");
        }
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<List<Authors>> findAllAuthors(@RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 2);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Authors> authors = apiService.findAllAuthors(pageable);

        if (!authors.isEmpty())
            return new ResponseEntity<>(authors.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Author");
    }
}


























