package com.codesoom.assignment;

import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUserDataFactory {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createValidUserJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(createValidUserData());
    }

    public String createValidUserJson(Long id) throws JsonProcessingException {
        return objectMapper.writeValueAsString(createValidUserData(id));
    }

    public UserData createValidUserData() {
        return validUserDataBuilder().build();
    }

    public UserData createValidUserData(Long id) {
        return validUserDataBuilder().id(id)
                .build();
    }

    private UserData.UserDataBuilder validUserDataBuilder() {
        return UserData.builder()
                .name("name")
                .email("email")
                .password("password");
    }
}
