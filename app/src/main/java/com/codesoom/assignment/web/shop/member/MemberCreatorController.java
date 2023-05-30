package com.codesoom.assignment.web.shop.member;


import com.codesoom.assignment.application.member.MemberCreator;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.web.shop.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberCreatorController {
    private final MemberCreator memberCreator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        return memberCreator.create(memberCreateRequest);
    }
}
