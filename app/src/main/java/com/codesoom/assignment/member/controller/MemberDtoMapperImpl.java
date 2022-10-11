package com.codesoom.assignment.member.controller;

import com.codesoom.assignment.member.application.MemberCommand;
import com.codesoom.assignment.member.controller.MemberDto.RequestParam;
import org.springframework.stereotype.Component;

@Component
public class MemberDtoMapperImpl implements MemberDtoMapper {
    @Override
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

    @Override
    public MemberCommand.UpdateRequest of(Long id, MemberDto.UpdateParam updateParam) {
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
