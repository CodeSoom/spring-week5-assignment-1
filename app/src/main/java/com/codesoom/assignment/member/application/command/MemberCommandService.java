package com.codesoom.assignment.member.application.command;

import com.codesoom.assignment.member.application.MemberCommand;
import com.codesoom.assignment.member.application.MemberInfo;

public interface MemberCommandService {

    MemberInfo createMember(MemberCommand.Register command);

    MemberInfo updateMember(MemberCommand.UpdateReq command);

    void deleteMember(Long id);

}
