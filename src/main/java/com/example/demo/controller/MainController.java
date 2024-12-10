package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 점원, service: 요리사
public class MainController {
    @GetMapping("/")
    @ResponseBody
    public int home() {
        System.out.println("Home Page Requested");
        return 22;
    }

    @GetMapping("/about")
    @ResponseBody
    public String about() {
        System.out.println("about Page Requested");
        return "안녕하세요";
    }
}
