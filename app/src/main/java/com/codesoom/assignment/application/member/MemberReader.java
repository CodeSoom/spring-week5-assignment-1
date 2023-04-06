package com.codesoom.assignment.application.member;

import com.codesoom.assignment.MemberNotFoundException;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }
}
