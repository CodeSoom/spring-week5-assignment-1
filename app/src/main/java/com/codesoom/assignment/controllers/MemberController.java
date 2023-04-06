package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.MemberService;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.dto.MemberData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@RequestBody @Valid MemberData memberData) {
        return memberService.create(memberData);
    }

    @PatchMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody @Valid MemberData memberData) {
        System.out.println("long " + id);
        System.out.println("MemberData " + memberData.toString());
        return memberService.updateMember(id, memberData);
    }
}
