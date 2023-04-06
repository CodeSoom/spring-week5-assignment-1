package com.codesoom.assignment.web.shop.member.dto;

import com.codesoom.assignment.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateRequest extends MemberRequest {

    @Builder
    public MemberCreateRequest(String name, String phone) {
        super(name, phone);
    }

    public Member toMember() {
        return Member.builder()
                .name(this.name)
                .phone(this.phone)
                .build();
    }
    @Override
    public String toString() {
        return "{\"name\":\"" + this.name + "\", \"phone\": \"" + this.phone + "\"}";
    }
}
