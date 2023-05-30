package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberReader;
import com.codesoom.assignment.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberReaderController {

    private final MemberReader memberReader;

    @GetMapping("/{id}")
    public Member read(@PathVariable Long id) {
        return memberReader.read(id);
    }
}
