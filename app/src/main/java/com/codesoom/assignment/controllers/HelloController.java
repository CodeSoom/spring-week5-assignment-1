package com.codesoom.assignment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    /**
     * 인사를 요청합니다.
     * @return
     */
    @RequestMapping("/")
    public String sayHello() {
        return "Hello, world!";
    }
}
