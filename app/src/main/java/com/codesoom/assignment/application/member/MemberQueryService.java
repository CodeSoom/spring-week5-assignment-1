package com.codesoom.assignment.application.member;

import com.codesoom.assignment.application.member.MemberInfo;
import com.codesoom.assignment.domain.member.Member;

import java.util.List;

public interface MemberQueryService {

    List<Member> getMembers();

    Member getMember(Long id);

}
