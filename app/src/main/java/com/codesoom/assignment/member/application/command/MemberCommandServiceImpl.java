package com.codesoom.assignment.member.application.command;

import com.codesoom.assignment.member.application.MemberCommand;
import com.codesoom.assignment.member.application.MemberInfo;
import com.codesoom.assignment.member.common.exception.MemberNotFoundException;
import com.codesoom.assignment.member.domain.Member;
import com.codesoom.assignment.member.domain.MemberRepository;
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
    public MemberInfo updateMember(MemberCommand.UpdateReq command) {
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
