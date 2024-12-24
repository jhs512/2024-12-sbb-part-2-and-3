package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 점원, service: 요리사
public class MainController {
    @GetMapping("/")
    public String home() {
        return "redirect:/question/list";
    }

    @GetMapping("/about")
    @ResponseBody
    public String about() {
        System.out.println("about Page Requested");
        return "안녕하세요";
    }
}
