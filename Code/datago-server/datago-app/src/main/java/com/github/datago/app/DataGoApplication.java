package com.github.datago.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.datago")
public class DataGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataGoApplication.class);
    }
}
