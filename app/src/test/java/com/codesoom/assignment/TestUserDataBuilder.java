package com.codesoom.assignment;

import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUserDataBuilder {
    public static TestUserDataBuilder valid() {
        return new TestUserDataBuilder(
                UserData.builder()
                .name("name")
                .email("email")
                .password("password")
        );
    }

    public static TestUserDataBuilder invalid() {
        return new TestUserDataBuilder(
                UserData.builder()
                        .name("")
                        .email("")
                        .password("")
        );
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private UserData.UserDataBuilder baseBuilder;

    public TestUserDataBuilder(UserData.UserDataBuilder baseBuilder) {
        this.baseBuilder = baseBuilder;
    }

    public TestUserDataBuilder id(Long id) {
        this.baseBuilder = baseBuilder.id(id);
        return this;
    }

    public String buildJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(buildData());
    }

    public UserData buildData() {
        return baseBuilder.build();
    }
}
