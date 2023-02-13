package com.knoldus.service_a.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/a")
public class ServiceAController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String SERVICE_A = "service_A";
    private static final String BASE_URL
            ="http://localhost:9091/";

    int count=1;

    @GetMapping
    @Retry(name = SERVICE_A)
    public String serviceA(){
        String url = BASE_URL + "/b";
        System.out.println("Retry method called" + count++ +"times at" + new Date());
        return restTemplate.getForObject(
                url,
                String.class
        );
    }
    public String serviceAFallback(Exception e){
        return "This is the fallback method for Service A";
    }
}
