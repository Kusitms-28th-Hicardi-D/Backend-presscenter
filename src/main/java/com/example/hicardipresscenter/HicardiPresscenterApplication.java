package com.example.hicardipresscenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
public class HicardiPresscenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HicardiPresscenterApplication.class, args);
    }

}
