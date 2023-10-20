package com.example.productapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.models.annotations.OpenAPI30;

@SpringBootApplication
@OpenAPI30
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}