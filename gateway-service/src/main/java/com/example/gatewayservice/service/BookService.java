package com.example.gatewayservice.service;

import com.example.gatewayservice.pojos.Book;
import com.example.gatewayservice.pojos.CatalogItem;
import com.example.gatewayservice.pojos.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookService {

    private final RestTemplate restTemplate;

//when to time out
    //number of requests to take a look at
    //% of threshold requests that failed
    //how long before retrying
    //thread pool key
    //max 20 threads waiting for responds
    //max 10 threads to stay in the queue
    @HystrixCommand(fallbackMethod = "getBookFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") },
            threadPoolKey = "booksData", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10") })
    public CatalogItem getBookData(Rating rating) {
        Book book = restTemplate.getForObject("http://book-service/books/" + rating.getBookId(), Book.class);
        return new CatalogItem(book.getName(), book.getDesc(), rating.getRating());
    }

    private CatalogItem getBookFallback(Rating rating) {
        return new CatalogItem("Error", "empty", 0);
    }
}
