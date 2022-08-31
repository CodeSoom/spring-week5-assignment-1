package com.codesoom.assignment;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.application.UserQueryService;
import com.codesoom.assignment.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {

    public static final long TEST_SIZE = 3L;

    public static List<User> addNewUsers(UserCommandService command, Long size){
        List<User> users = new ArrayList<>();
        for(long i = 1 ; i <= size ; i++){
            users.add(addNewUser(command , i));
        }
        return users;
    }

    public static User addNewUser(UserCommandService command , Long number){
        return command.save(User.builder()
                                    .name("홍길동" + number)
                                    .email("test@gmail.com" + number)
                                    .password("password" + number)
                                    .build());
    }

    public static void repositoryClear(UserQueryService query , UserCommandService command){
        for(User user : query.findAll()){
            command.delete(user);
        }
    }
}
