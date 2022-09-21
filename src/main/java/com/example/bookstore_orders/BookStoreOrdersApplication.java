package com.example.bookstore_orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class BookStoreOrdersApplication {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(BookStoreOrdersApplication.class, args);
        System.out.println("--------------------------------");
        log.info("\nHello! Welcome to Book Store Project!");
    }
}
