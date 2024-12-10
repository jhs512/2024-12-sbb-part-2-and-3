package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 점원, service: 요리사
public class MainController {
    @GetMapping("/")
    public void home() {
        System.out.println("Home Page Requested");
    }

    @GetMapping("/about")
    public void about() {
        System.out.println("about Page Requested");
    }
}
