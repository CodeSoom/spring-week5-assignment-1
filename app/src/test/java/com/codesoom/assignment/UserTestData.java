package com.codesoom.assignment;

import com.codesoom.assignment.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {

    public static List<User> newUsers(Long size){
        List<User> users = new ArrayList<>();
        for(long i = 0 ; i < size ; i++){
            users.add(newUser(i));
        }
        return users;
    }

    public static User newUser(Long number){
        return new User(number , "홍길동" , "test@email.com" , "1234");
    }
}
