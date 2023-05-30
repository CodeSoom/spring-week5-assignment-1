package com.codesoom.assignment.application.member;

import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDeleter {
    private final MemberReader memberReader;
    private final MemberRepository memberRepository;

    public boolean delete(long id) {
        Member member = memberReader.read(id);
        if (member.isGhost()) {
            return false;
        }

        memberRepository.delete(member);
        return true;
    }
}
