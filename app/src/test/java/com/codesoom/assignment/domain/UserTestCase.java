package com.codesoom.assignment.domain;

import java.util.ArrayList;
import java.util.List;

public class UserTestCase {
    private static List<User> users = null;

    private UserTestCase() {
    }

    public static List<User> getTestUsers(int userCount) {
        if (users == null) {
            users = new ArrayList<>();
            for (int i = 0; i < userCount; i++) {
                users.add(new User("철수" + i, "a" + i + "@example.com", "abc" + i));
            }
        }
        return users;
    }

}
