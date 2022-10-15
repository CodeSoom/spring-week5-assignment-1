package com.codesoom.assignment.application.member.implement;

import com.codesoom.assignment.application.member.MemberCommand;
import com.codesoom.assignment.application.member.MemberCommandService;
import com.codesoom.assignment.common.exception.MemberNotFoundException;
import com.codesoom.assignment.common.mapper.MemberMapper;
import com.codesoom.assignment.domain.member.Member;
import com.codesoom.assignment.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberCommandServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Transactional
    @Override
    public Member createMember(MemberCommand.Register command) {
        return memberRepository.save(memberMapper.toEntity(command));
    }

    /**
     * @throws MemberNotFoundException 회원정보가 없을 경우
     */
    @Transactional
    @Override
    public Member updateMember(MemberCommand.UpdateRequest command) {
        Member source = memberMapper.toEntity(command);
        Member findMember = getFindMember(source.getId());

        findMember.modifyMemberInfo(source);

        return findMember;
    }

    /**
     * @throws MemberNotFoundException 회원정보가 없을 경우
     */
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
