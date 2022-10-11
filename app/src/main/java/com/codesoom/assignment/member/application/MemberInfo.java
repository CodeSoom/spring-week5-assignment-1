package com.codesoom.assignment.member.application;

import com.codesoom.assignment.member.domain.Member;
import lombok.Getter;
import lombok.ToString;

@lombok.Generated
@Getter
@ToString
public class MemberInfo {
    private final Long id;

    private final String name;

    private final String password;

    private final String email;

    public MemberInfo(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.password = member.getPassword();
        this.email = member.getEmail();
    }
}
