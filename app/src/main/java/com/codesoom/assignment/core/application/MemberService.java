package com.codesoom.assignment.core.application;

import com.codesoom.assignment.core.domain.Member;
import com.codesoom.assignment.core.domain.MemberRepository;
import com.codesoom.assignment.web.dto.MemberData;
import com.codesoom.assignment.web.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // TODO 1. 회원 생성하기

    /**
     * 신규 회원 정보를 저장합니다.
     * @param memberData
     * @return 저장한 회원
     */
    public Member saveMember(MemberData memberData) {
        Member member = Member.builder()
                .name(memberData.getName())
                .password(memberData.getPassword())
                .email(memberData.getEmail())
                .build();

        return memberRepository.save(member);
    }

    // TODO 2. 회원 삭제하기
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        memberRepository.delete(member);
    }

    // TODO 3. 회원 수정하기
    public Member updateMember(Long id, MemberData memberData) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        member.changeWith(
                memberData.getName(),
                memberData.getPassword(),
                memberData.getEmail()
        );

//        return memberRepository.save(member);
        return member;
    }

}
