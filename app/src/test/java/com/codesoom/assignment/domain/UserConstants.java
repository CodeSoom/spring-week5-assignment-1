package com.codesoom.assignment.domain;

public interface UserConstants {
    final Long ID = 1L;
    final String NAME = "테스트 유저";
    final String PASSWORD = "password";
    final String EMAIL = "test@test.com";
    final User USER = new User(ID, EMAIL, NAME, PASSWORD);
}
