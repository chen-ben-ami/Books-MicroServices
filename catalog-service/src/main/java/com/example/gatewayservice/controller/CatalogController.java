package com.example.gatewayservice.controller;

import com.example.gatewayservice.pojos.CatalogItem;
import com.example.gatewayservice.pojos.UserRating;
import com.example.gatewayservice.service.BookService;
import com.example.gatewayservice.service.UserRatingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final BookService bookService;
    private final UserRatingService userRatingService;

    @GetMapping("{userId}")
    @HystrixCommand(fallbackMethod = "getCatalogItemsFallback")
    public List<CatalogItem> getCatalogItems(@PathVariable String userId) {
        UserRating ratings = userRatingService.getUserRating(userId);
        return ratings.getRatingList().stream().map(bookService::getBookData)
                .collect(Collectors.toList());
    }

    public List<CatalogItem> getCatalogItemsFallback(String userId) {
        return Arrays.asList(new CatalogItem("No Book", "empty", 0));
    }
}