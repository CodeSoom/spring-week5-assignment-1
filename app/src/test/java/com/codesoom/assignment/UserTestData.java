package com.codesoom.assignment;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {

    public static final long TEST_SIZE = 3L;

    public static List<User> addNewUsers(UserService service, Long size){
        List<User> users = new ArrayList<>();
        for(long i = 1 ; i <= size ; i++){
            users.add(addNewUser(service , i));
        }
        return users;
    }

    public static User addNewUser(UserService service , Long number){
        return service.save(User.builder()
                                    .name("홍길동" + number)
                                    .email("test@gmail.com" + number)
                                    .password("password" + number)
                                    .build());
    }

    public static void repositoryClear(UserService service){
        for(User user : service.findAll()){
            service.delete(user.getId());
        }
    }
}
