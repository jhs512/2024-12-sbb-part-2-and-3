package com.mysite.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }

    @GetMapping("/about")
    @ResponseBody
    public String about() {
        System.out.println("about");
        return "안녕하세요.";
    }

    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    public void contact() {
        System.out.println("contact");
    }
}
