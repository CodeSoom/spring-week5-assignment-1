package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.MemberService;
import com.codesoom.assignment.domain.Member;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public Member create(String name, String phone) {
        return Member.builder()
                .name(name)
                .phone(phone)
                .build();
    }
}
