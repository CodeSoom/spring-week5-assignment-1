package com.codesoom.assignment.application.member;

import com.codesoom.assignment.application.member.MemberInfo;

import java.util.List;

public interface MemberQueryService {

    List<MemberInfo> getMembers();

    MemberInfo getMember(Long id);

}
