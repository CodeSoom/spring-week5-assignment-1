package com.codesoom.assignment.web.shop.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    protected String name;
    protected String phone;

    public MemberRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
