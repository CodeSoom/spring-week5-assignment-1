package com.codesoom.assignment.application;

import com.codesoom.assignment.MemberNotFoundException;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.domain.MemberRepository;
import com.codesoom.assignment.dto.MemberData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member create(MemberData memberData) {
        return Member.builder()
                .name(memberData.getName())
                .phone(memberData.getPhone())
                .build();
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }
}
