package com.codesoom.assignment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * 서버 생존 확인용으로 String을 응답합니다.
     *
     * @return String
     */
    @RequestMapping("/")
    public String sayHello() {
        return "Hello, world!";
    }
}
