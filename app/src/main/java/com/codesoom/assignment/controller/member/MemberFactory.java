package com.codesoom.assignment.controller.member;

import com.codesoom.assignment.application.member.MemberCommand;

public class MemberFactory {
    public static MemberCommand.Register of(MemberDto.RequestParam request) {
        if (request == null) {
            return null;
        }

        return MemberCommand.Register.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

    public static MemberCommand.UpdateRequest of(Long id, MemberDto.UpdateParam updateParam) {
        if (id == null || updateParam == null) {
            return null;
        }

        return MemberCommand.UpdateRequest.builder()
                .id(id)
                .name(updateParam.getName())
                .password(updateParam.getPassword())
                .email(updateParam.getEmail())
                .build();
    }
}
