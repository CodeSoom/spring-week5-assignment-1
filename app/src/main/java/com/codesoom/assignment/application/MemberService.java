package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.dto.MemberData;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public Member create(MemberData memberData) {
        return Member.builder()
                .name(memberData.getName())
                .phone(memberData.getPhone())
                .build();
    }
}
