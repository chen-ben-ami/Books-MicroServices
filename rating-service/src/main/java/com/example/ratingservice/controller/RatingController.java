package com.example.ratingservice.controller;

import com.example.ratingservice.pojos.Rating;
import com.example.ratingservice.pojos.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @GetMapping("/{bookId}")
    public Rating getRating(@PathVariable String bookId) {
        return new Rating(bookId, 4);
    }

    @GetMapping("users/{userId}")
    public UserRating getRatings(@PathVariable String userId) {
        return new UserRating(userId,Arrays.asList(new Rating("1234", 4),
                new Rating("5678", 3)));
    }
}
