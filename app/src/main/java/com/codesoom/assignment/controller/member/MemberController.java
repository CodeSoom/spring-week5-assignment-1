package com.codesoom.assignment.controller.member;

import com.codesoom.assignment.application.member.MemberCommand;
import com.codesoom.assignment.application.member.MemberCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/users")
public class MemberController {
    private final MemberCommandService memberService;
    private final MemberDtoMapper memberDtoMapper;

    public MemberController(MemberCommandService memberService, MemberDtoMapper memberDtoMapper) {
        this.memberService = memberService;
        this.memberDtoMapper = memberDtoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto.MemberInfo registerMember(@RequestBody @Valid MemberDto.RequestParam request) {
        final MemberCommand.Register command = memberDtoMapper.of(request);
        return new MemberDto.MemberInfo(memberService.createMember(command));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public MemberDto.MemberInfo updateMember(@PathVariable Long id, @RequestBody @Valid MemberDto.UpdateParam request) {
        final MemberCommand.UpdateRequest command = memberDtoMapper.of(id, request);
        return new MemberDto.MemberInfo(memberService.updateMember(command));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
