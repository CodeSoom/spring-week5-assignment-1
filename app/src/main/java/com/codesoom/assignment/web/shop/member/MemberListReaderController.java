package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberListReader;
import com.codesoom.assignment.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberListReaderController {
    private final MemberListReader memberListReader;

    @GetMapping
    public List<Member> getMembers() {
        return memberListReader.read();
    }


}
