package com.codesoom.assignment.core.application;

import com.codesoom.assignment.core.domain.Member;
import com.codesoom.assignment.core.domain.MemberRepository;
import com.codesoom.assignment.web.dto.MemberData;
import com.codesoom.assignment.web.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 신규 회원를 등록합니다.
     * @param  memberData - 신규 등록할 회원 정보(이름, 비밀번호, 이메일 등).
     * @return member - 고유 ID를 발급받은 신규 회원.
     */
    public Member saveMember(MemberData memberData) {
        Member member = Member.builder()
                .name(memberData.getName())
                .password(memberData.getPassword())
                .email(memberData.getEmail())
                .build();

        return memberRepository.save(member);
    }

    /**
     * 기존 회원를 삭제합니다.
     * @param  id - 삭제할 특정 회원의 고유 ID.
     * @throws MemberNotFoundException - ID에 해당하는 회원이 없다면, 회원 정보를 찾을 수 없다는 에러 발생.
     */
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        memberRepository.delete(member);
    }

    /**
     * 기존 회원 정보를 갱합니다.
     * @param id - 갱신할 특정 회원의 고유 ID.
     * @param memberData - 갱신할 회원의 정보(이름, 비밀번호, 이메일 등).
     * @return member - 갱신한 회원 정보 반환.
     * @throws MemberNotFoundException - ID에 해당하는 회원이 없다면, 회원 정보를 찾을 수 없다는 에러 발생.
     */
    public Member updateMember(Long id, MemberData memberData) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        member.changeWith(
                memberData.getName(),
                memberData.getPassword(),
                memberData.getEmail()
        );

        return member;
    }

}
