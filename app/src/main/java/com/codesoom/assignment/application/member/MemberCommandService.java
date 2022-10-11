package com.codesoom.assignment.application.member;

import com.codesoom.assignment.domain.member.Member;

public interface MemberCommandService {

    Member createMember(MemberCommand.Register command);

    Member updateMember(MemberCommand.UpdateRequest command);

    void deleteMember(Long id);

}
