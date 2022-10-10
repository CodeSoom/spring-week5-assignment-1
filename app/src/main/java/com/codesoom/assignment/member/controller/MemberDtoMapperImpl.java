package com.codesoom.assignment.member.controller;

import com.codesoom.assignment.member.application.MemberCommand;
import com.codesoom.assignment.member.controller.MemberDto.RequestParam;
import org.springframework.stereotype.Component;

@Component
public class MemberDtoMapperImpl implements MemberDtoMapper {
    public MemberCommand.Register of(RequestParam request) {
        if (request == null) {
            return null;
        }

        return MemberCommand.Register.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

    public MemberCommand.UpdateRequest of(Long id, RequestParam requestParam) {
        if (id == null || requestParam == null) {
            return null;
        }

        return MemberCommand.UpdateRequest.builder()
                .id(id)
                .name(requestParam.getName())
                .password(requestParam.getPassword())
                .email(requestParam.getEmail())
                .build();
    }
}
