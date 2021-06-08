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
     * 신규 회원을 등록하고, 등록된 회원을 리턴합니다.
     * @param  memberData 신규 등록할 회원 정보.
     * @return member 등록된 신규 회원.
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
     * 회원 정보를 찾아 삭제합니다.
     * @param  id 삭제할 회원의 식별자.
     * @throws MemberNotFoundException 회원을 찾을 수 없는 경우.
     */
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        memberRepository.delete(member);
    }

    /**
     * 회원 정보를 찾아 갱합니다.
     * @param id 갱신할 회원의 식별자.
     * @param memberData - 갱신할 회원의 정보(이름, 비밀번호, 이메일 등).
     * @return member - 갱신된 회원.
     * @throws MemberNotFoundException 회원을 찾을 수 없는 경우.
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
