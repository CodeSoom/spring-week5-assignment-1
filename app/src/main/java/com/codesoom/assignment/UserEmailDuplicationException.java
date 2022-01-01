package com.codesoom.assignment;

public class UserEmailDuplicationException extends RuntimeException{
    public UserEmailDuplicationException(String email) {
        super("User email is alreay exiseted : " + email);
    }
}
