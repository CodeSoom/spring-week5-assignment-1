package com.codesoom.assignment.application.member.implement;

import com.codesoom.assignment.application.member.MemberCommand;
import com.codesoom.assignment.application.member.MemberCommandService;
import com.codesoom.assignment.application.member.MemberInfo;
import com.codesoom.assignment.common.exception.MemberNotFoundException;
import com.codesoom.assignment.domain.member.Member;
import com.codesoom.assignment.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;

    public MemberCommandServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public MemberInfo createMember(MemberCommand.Register command) {
        return new MemberInfo(memberRepository.save(command.toEntity()));
    }

    @Transactional
    @Override
    public MemberInfo updateMember(MemberCommand.UpdateRequest command) {
        Member source = command.toEntity();
        Member findMember = getFindMember(source.getId());

        findMember.modifyMemberInfo(source);

        return new MemberInfo(findMember);
    }

    @Transactional
    @Override
    public void deleteMember(Long id) {
        Member findMember = getFindMember(id);

        memberRepository.delete(findMember);
    }

    private Member getFindMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }
}
