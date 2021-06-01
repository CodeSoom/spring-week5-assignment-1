package com.codesoom.assignment.core.application;

import com.codesoom.assignment.core.domain.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // TODO 1. 회원 생성하기


    // TODO 2. 회원 삭제하기


    // TODO 3. 회원 수정하기

}
