package com.codesoom.assignment.member.controller.command;

import com.codesoom.assignment.member.application.MemberCommand;
import com.codesoom.assignment.member.application.MemberInfo;
import com.codesoom.assignment.member.application.command.MemberCommandService;
import com.codesoom.assignment.member.controller.MemberDto.RequestParam;
import com.codesoom.assignment.member.controller.MemberDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class MemberCommandController {

    private final MemberCommandService memberService;

    private final MemberDtoMapper memberDtoMapper;

    public MemberCommandController(MemberCommandService memberService, MemberDtoMapper memberDtoMapper) {
        this.memberService = memberService;
        this.memberDtoMapper = memberDtoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberInfo registerMember(@RequestBody @Validated RequestParam request) {
        final MemberCommand.Register command = memberDtoMapper.of(request);
        return memberService.createMember(command);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public MemberInfo updateMember(@PathVariable Long id, @RequestBody @Validated RequestParam request) {
        final MemberCommand.UpdateRequest command = memberDtoMapper.of(id, request);
        return memberService.updateMember(command);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}