package com.codesoom.assignment.member.common;

import com.codesoom.assignment.member.domain.Member;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MemberFactory {

    private static final Random random = new Random();

    public static Member createMember(Long id) {
        Member.MemberBuilder member = Member.builder();

        System.out.println(member.toString());

        member.id(id)
                .name(getRandomName())
                .password(UUID.randomUUID().toString())
                .email("test" + random.nextInt(100) + "@gmail.com");

        return member.build();
    }

    public static Member createMember() {
        return Member.builder()
                .name(getRandomName())
                .password(UUID.randomUUID().toString())
                .email("test" + random.nextInt(100) + "@gmail.com")
                .build();
    }

    private static String getRandomName() {
        List<String> lastName = Arrays.asList("김", "이", "박", "최");
        List<String> firstName = Arrays.asList("가", "강", "건", "경", "고", "관",
                                                "광", "구", "규", "근", "기", "길",
                                                "나", "남", "노", "누", "다", "단",
                                                "달", "담", "대", "덕", "도", "동",
                                                "두", "라", "래", "로", "루", "리",
                                                "마", "만", "명", "무", "문", "미",
                                                "민", "바", "박", "백", "범", "별");
        Collections.shuffle(lastName);
        Collections.shuffle(firstName);

        return lastName.get(0) + firstName.get(0) + firstName.get(1);
    }
}
