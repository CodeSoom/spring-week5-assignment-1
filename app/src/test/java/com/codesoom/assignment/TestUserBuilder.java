package com.codesoom.assignment;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUserBuilder {
    public static TestUserBuilder presetAllFields() {
        return new TestUserBuilder()
                .name("name")
                .email("email")
                .password("password");
    }

    public static TestUserBuilder presetNoFields() {
        return new TestUserBuilder();
    }

    public TestUserBuilder id(Long id) {
        this.userBuilder = userBuilder.id(id);
        this.userDataBuilder = userDataBuilder.id(id);
        return this;
    }

    public TestUserBuilder name(String name) {
        this.userBuilder = userBuilder.name(name);
        this.userDataBuilder = userDataBuilder.name(name);
        return this;
    }

    public TestUserBuilder email(String email) {
        this.userBuilder = userBuilder.email(email);
        this.userDataBuilder = userDataBuilder.email(email);
        return this;
    }

    public TestUserBuilder password(String password) {
        this.userBuilder = userBuilder.password(password);
        this.userDataBuilder = userDataBuilder.password(password);
        return this;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    private User.UserBuilder userBuilder;
    private UserData.UserDataBuilder userDataBuilder;

    public TestUserBuilder() {
        this.userBuilder = User.builder();
        this.userDataBuilder = UserData.builder();
    }

    public String buildJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(buildData());
    }

    public UserData buildData() {
        return userDataBuilder.build();
    }

    public User buildUser() {
        return userBuilder.build();
    }
}
