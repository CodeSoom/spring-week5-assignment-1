package com.codesoom.assignment.application.member;

import com.codesoom.assignment.domain.member.Member;

import java.util.List;

public interface MemberQueryService {

    /**
     * 전체 회원정보를 리턴한다.
     * @return 전체 회원정보
     */
    List<Member> getMembers();

    /**
     * 회워 ID의 회원정보를 리턴한다.
     * @param id 회원 ID
     * @return 검색된 회원정보
     */
    Member getMember(Long id);

}
