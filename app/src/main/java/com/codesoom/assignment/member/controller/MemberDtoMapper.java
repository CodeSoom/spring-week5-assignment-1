package com.codesoom.assignment.member.controller;

import com.codesoom.assignment.member.application.MemberCommand;

public interface MemberDtoMapper {
    MemberCommand.Register of(MemberDto.RequestParam request);

    MemberCommand.UpdateRequest of(Long id, MemberDto.RequestParam requestParam);
}
