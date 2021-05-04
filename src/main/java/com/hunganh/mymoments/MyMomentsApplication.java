package com.hunganh.mymoments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MyMomentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMomentsApplication.class, args);
    }

}
