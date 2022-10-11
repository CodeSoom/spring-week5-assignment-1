package com.codesoom.assignment.application.member;

public interface MemberCommandService {

    MemberInfo createMember(MemberCommand.Register command);

    MemberInfo updateMember(MemberCommand.UpdateRequest command);

    void deleteMember(Long id);

}
