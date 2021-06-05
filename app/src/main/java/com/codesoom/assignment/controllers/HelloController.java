package com.codesoom.assignment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello API의 요청을 처리합니다.
 */
@RestController
public class HelloController {
    /**
     * 환영 인사를 리턴합니다.
     *
     * @return 환영 인사 문자열
     */
    @RequestMapping("/")
    public String sayHello() {
        return "Hello, world!";
    }
}
