package com.codesoom.assignment;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserDto;

public class UserBaseTest {
    protected static final String USER_NAME = "aa";
    protected static final String USER_EMAIL = "aa@gmail.com";
    protected static final String USER_PASSWORD = "aa";
    protected static final String ERROR_MSG = "User not found";

    protected UserDto supplyDummyUserDto() {
        UserDto userDto = new User(USER_NAME, USER_EMAIL, USER_PASSWORD);

        return userDto;
    }


    protected String supplyErrorMSG(Long id){
        return ERROR_MSG + ": " + id;
    }
}
