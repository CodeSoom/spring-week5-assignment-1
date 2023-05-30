package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberUpdater;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.web.shop.member.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberUpdaterController {

    private final MemberUpdater memberUpdater;

    @PatchMapping
    public Member update(@RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        return memberUpdater.update(memberUpdateRequest);
    }
}
