package com.BookShop.BookShopAPI.controller;

import com.BookShop.BookShopAPI.entity.Reviews;
import com.BookShop.BookShopAPI.entity.Transactions;
import com.BookShop.BookShopAPI.entity.Users;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.exception.NotFoundException;
import com.BookShop.BookShopAPI.service.APIService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewsController {

    private final APIService apiService;

    public ReviewsController(APIService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(value = "/review")
    public ResponseEntity<Reviews> insertReview(@RequestBody Reviews review){
        Users user = apiService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        review.setUserId(user.getUserId());

        Reviews reviewInserted = apiService.insertReview(review);
        if (reviewInserted != null)
            return new ResponseEntity<>(reviewInserted, HttpStatus.CREATED);
        else
            throw new BadRequestException("Couldn't insert review");
    }

    @GetMapping(value = "/admin/reviews/user")
    public ResponseEntity<List<Reviews>> findReviewByUserId(@RequestParam String userId,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Reviews> reviews = apiService.findReviewsByUserId(userId, pageable);

        if (!reviews.getContent().isEmpty())
            return new ResponseEntity<>(reviews.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Review with given input!");
    }

    @GetMapping(value = "/reviews")
    public ResponseEntity<List<Reviews>> findMyReviews(@RequestParam(required = false) Integer size,
                                                       @RequestParam(required = false) Integer pageNumber){
        Users user = apiService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Reviews> reviews = apiService.findReviewsByUserId(user.getUserId(), pageable);

        if (!reviews.getContent().isEmpty())
            return new ResponseEntity<>(reviews.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Review with given input!");
    }

    @GetMapping(value = "/reviews/book")
    public ResponseEntity<List<Reviews>> findReviewByBookId(@RequestParam String bookId,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Reviews> reviews = apiService.findReviewsByBookId(bookId, pageable);

        if (!reviews.getContent().isEmpty())
            return new ResponseEntity<>(reviews.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Review with given input!");
    }

    @GetMapping(value = "/reviews/rating")
    public ResponseEntity<List<Reviews>> findReviewByRating(@RequestParam double rating,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) Integer pageNumber){

        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }

        Page<Reviews> reviews = apiService.findReviewsByRating(rating, pageable);

        if (!reviews.getContent().isEmpty())
            return new ResponseEntity<>(reviews.getContent(), HttpStatus.OK);
        else
            throw new NotFoundException("Couldn't find any Review with given input!");
    }

    @DeleteMapping(value = "/admin/review/user")
    public ResponseEntity<String> deleteReviewByUserIdAndBookId(@RequestParam String userId){
        List<Reviews> reviews = apiService.findReviewsByUserId(userId);
        if (reviews != null) {
            reviews.forEach(review -> apiService.deleteReviewByUserIdAndBookId(review.getUserId(), review.getBookId()));
            return new ResponseEntity<>("Successfully deleted all Reviews for user: " +
                    apiService.findUserById(userId).getEmail(), HttpStatus.OK);
        }else
            throw new NotFoundException("Couldn't delete Review with given input!");
    }
}
