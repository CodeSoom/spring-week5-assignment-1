package com.codesoom.assignment.application.member.implement;

import com.codesoom.assignment.application.member.MemberQueryService;
import com.codesoom.assignment.common.exception.MemberNotFoundException;
import com.codesoom.assignment.domain.member.Member;
import com.codesoom.assignment.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;

    public MemberQueryServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    /**
     * @throws MemberNotFoundException 회원정보가 없을 경우
     */
    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }
}
