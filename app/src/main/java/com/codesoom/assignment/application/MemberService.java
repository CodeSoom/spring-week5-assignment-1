package com.codesoom.assignment.application;

import com.codesoom.assignment.MemberNotFoundException;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.domain.MemberRepository;
import com.codesoom.assignment.dto.MemberData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member create(MemberData memberData) {
        Member createdMember = Member.builder()
                .name(memberData.getName())
                .phone(memberData.getPhone())
                .build();
        memberRepository.save(createdMember);
        return createdMember;
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public Member updateMember(Long id, MemberData memberData) {
        Member member = getMember(id);
        member.update(memberData.getName(), member.getPhone());
        return member;
    }
}
