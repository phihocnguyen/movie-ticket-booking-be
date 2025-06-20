package com.example.movieticketbookingbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.movieticketbookingbe.model")
@EnableJpaRepositories("com.example.movieticketbookingbe.repository")
public class MovieTicketBookingBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTicketBookingBeApplication.class, args);
    }
}