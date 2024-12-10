package com.ann.annovation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// @Controller: 이 클래스가 웹 요청을 처리하는 컨트롤러임을 나타냅니다.
@Controller
public class MainController {

    // MainController 클래스는 HTTP 요청을 처리하는 역할을 합니다.
    // Spring Boot는 이 클래스를 자동으로 스캔하고 관리합니다.

    // @GetMapping: 특정 URL 경로로 들어오는 HTTP GET 요청을 처리하도록 매핑합니다.

    // GET 요청으로 루트 경로("/")에 접근했을 때 실행됩니다.
    // URL: http://localhost:8080/
    @GetMapping("/")
    public void home() {
        // 요청이 처리되면, 서버 콘솔에 "home"이라는 메시지를 출력합니다.
        System.out.println("home");
    }

    // GET 요청으로 "/about" 경로에 접근했을 때 실행됩니다.
    // URL: http://localhost:8080/about
    @GetMapping("/about")
    public void about() {
        System.out.println("about");
    }
}
