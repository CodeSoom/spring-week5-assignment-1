package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("존재하지 않는 user 입니다.");
    }
}
