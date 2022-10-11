package com.codesoom.assignment.application.member.implement;

import com.codesoom.assignment.application.member.MemberInfo;
import com.codesoom.assignment.application.member.MemberQueryService;
import com.codesoom.assignment.common.exception.MemberNotFoundException;
import com.codesoom.assignment.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;

    public MemberQueryServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberInfo> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public MemberInfo getMember(Long id) {
        return new MemberInfo(memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id)));
    }
}
