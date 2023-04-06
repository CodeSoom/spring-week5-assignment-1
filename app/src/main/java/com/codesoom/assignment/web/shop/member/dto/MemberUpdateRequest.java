package com.codesoom.assignment.web.shop.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateRequest extends MemberRequest {
    private Long id;

    @Builder
    public MemberUpdateRequest(String name, String phone, Long id) {
        super(name, phone);
        this.id = id;
    }
}
