package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.core.application.MemberService;
import com.codesoom.assignment.core.domain.Member;
import com.codesoom.assignment.web.dto.MemberData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 신규 회원을 등록합니다.
     * @param member
     * @return 신규 회원
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member registerMember(@RequestBody @Valid MemberData member) {
        return memberService.saveMember(member);
    }

    /**
     * 요청 ID에 대한 회원을 삭제합니다.
     * @param id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

    /**
     * 요청 ID에 대한 회원 정보를 수정합니다.
     * @param id
     * @param member
     * @return 수정한 회원
     */
    @PatchMapping("{id}")
    public Member modifyMember(@PathVariable Long id, @RequestBody MemberData member) {
        return memberService.updateMember(id, member);
    }

}
