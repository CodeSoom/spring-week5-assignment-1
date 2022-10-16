package com.codesoom.assignment.common.mapper;

import com.codesoom.assignment.application.member.MemberCommand;
import com.codesoom.assignment.controller.member.MemberDto;
import com.codesoom.assignment.domain.member.Member;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberCommand.Register of(MemberDto.RequestParam request);

    MemberCommand.UpdateRequest of(Long id, MemberDto.UpdateParam request);

    @Mapping(target = "id", ignore = true)
    Member toEntity(MemberCommand.Register command);

    Member toEntity(MemberCommand.UpdateRequest command);
}
