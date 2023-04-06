package com.codesoom.assignment.application.member;

import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.domain.MemberRepository;
import com.codesoom.assignment.web.shop.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCreator {
    private final MemberRepository memberRepository;

    public Member create(MemberCreateRequest memberCreateRequest) {
        Member createdMember = memberCreateRequest.toMember();
        Member result = memberRepository.save(createdMember);
        return result;
    }
}
