package com.codesoom.assignment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ROOT URL의 요청을 처리합니다.
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String sayHello() {
        return "Hello, world!";
    }
}
