package com.codesoom.assignment.controller.member;

import com.codesoom.assignment.application.member.MemberCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {
    MemberDtoMapper INSTANCE = Mappers.getMapper(MemberDtoMapper.class);

    MemberCommand.Register of(MemberDto.RequestParam request);

    MemberCommand.UpdateRequest of(Long id, MemberDto.UpdateParam request);
}
