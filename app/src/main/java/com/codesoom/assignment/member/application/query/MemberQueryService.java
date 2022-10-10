package com.codesoom.assignment.member.application.query;

import com.codesoom.assignment.member.application.MemberInfo;

import java.util.List;

public interface MemberQueryService {

    List<MemberInfo> getMembers();

    MemberInfo getMember(Long id);

}
