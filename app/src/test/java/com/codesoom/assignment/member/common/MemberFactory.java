package com.codesoom.assignment.member.common;

import com.codesoom.assignment.member.controller.MemberDto;
import com.codesoom.assignment.member.domain.Member;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.codesoom.assignment.member.common.MemberFactory.FieldName.EMAIL;
import static com.codesoom.assignment.member.common.MemberFactory.FieldName.NAME;
import static com.codesoom.assignment.member.common.MemberFactory.FieldName.PASSWORD;

public class MemberFactory {

    private static final Random random = new Random();

    public static Member createMember(Long id) {
        final Member.MemberBuilder member = Member.builder();

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

    public static MemberDto.RequestParam createRequestParam() {
        final MemberDto.RequestParam request = new MemberDto.RequestParam();
        request.setName(getRandomName());
        request.setPassword(UUID.randomUUID().toString());
        request.setEmail("test" + random.nextInt(100) + "@gmail.com");

        return request;
    }

    public static MemberDto.RequestParam createRequestParamWith(FieldName fieldName, ValueType valueType) {
        final MemberDto.RequestParam request = new MemberDto.RequestParam();
        String testValue;

        if (valueType == ValueType.NULL) {
            testValue = null;
        } else if (valueType == ValueType.EMPTY) {
            testValue = "";
        } else {
            testValue = "  ";
        }

        request.setName(fieldName == NAME ? testValue : getRandomName());
        request.setPassword(fieldName == PASSWORD ? testValue : UUID.randomUUID().toString());
        request.setEmail(fieldName == EMAIL ? testValue : "test" + random.nextInt(100) + "@gmail.com");

        return request;
    }

    private static String getRandomName() {
        final List<String> lastName = Arrays.asList("김", "이", "박", "최");
        final List<String> firstName = Arrays.asList("가", "강", "건", "경", "고", "관",
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

    public enum FieldName {
        NAME("name", "이름"),
        PASSWORD("password", "패스워드"),
        EMAIL("email", "이메일");

        private String name;
        private String description;

        FieldName(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ValueType {
        NULL, EMPTY, BLANK
    }
}
