package com.modhtom.fintech_wallet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
    @RequestMapping("/")
    public String Hello() {
        return "Visit www.modhtom.com";
    }
}
