package com.codesoom.assignment.application.member;

import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberListReader {
    private final MemberRepository memberRepository;

    public List<Member> read() {
        return memberRepository.findAll();
    }
}
