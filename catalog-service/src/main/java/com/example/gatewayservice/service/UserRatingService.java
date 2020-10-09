package com.example.gatewayservice.service;

import com.example.gatewayservice.pojos.Rating;
import com.example.gatewayservice.pojos.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserRatingService {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getRatingFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") },
            threadPoolKey = "userRatingData", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10") })
    public UserRating getUserRating(@PathVariable String userId) {
        return restTemplate.getForObject("http://rating-service/rating/users/" + userId,
                UserRating.class);
    }

    private UserRating getRatingFallback(String userId) {
        return new UserRating(userId, Arrays.asList(new Rating("0", 0)));
    }
}
