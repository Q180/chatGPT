package com.service;

import com.service.rocketMQ.Listener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class service {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(service.class, args);
        Listener.start();
    }
}
