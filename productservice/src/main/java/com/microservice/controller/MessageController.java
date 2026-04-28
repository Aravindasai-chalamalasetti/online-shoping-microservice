package com.microservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/prod")
public class MessageController {

    @Value("${spring.boot.message:Default Message}")
    private String message;

    @GetMapping("message")
    public String getMessage(){
        return message;
    }
}

