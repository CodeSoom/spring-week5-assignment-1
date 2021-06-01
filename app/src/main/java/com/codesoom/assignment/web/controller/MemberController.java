package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.core.domain.Member;
import com.codesoom.assignment.web.dto.MemberData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/user")
public class MemberController {

    // TODO 1. 회원 생성하기
    @PostMapping
    public Member registerMember(@RequestBody MemberData member) {
        return null;
    }

    // TODO 2. 회원 삭제하기
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable Long id) {

    }

    // TODO 3. 회원 수정하기
    @PatchMapping("{id}")
    public Member modifyMember(@PathVariable Long id, @RequestBody MemberData member) {
        return null;
    }

}
