package com.programming.notebook.swfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class FuntionalEndpointApplication {

    public static void main(String[] args) {

        log.info("Starting the application.");
        SpringApplication.run(FuntionalEndpointApplication.class, args);
    }
}
