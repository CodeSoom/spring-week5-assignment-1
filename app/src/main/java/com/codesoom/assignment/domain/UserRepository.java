package com.codesoom.assignment.domain;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository {
    User save(User user);

    void delete(User user);

    public class Fake implements UserRepository {
        private final List<User> users = new ArrayList<>();

        @Override
        public User save(User user) {
            final User created = new User(
                    Integer.toUnsignedLong(users.size() + 1),
                    user.email(),
                    user.name(),
                    user.password()
            );

            users.add(created);
            return created;
        }

        @Override
        public void delete(User user) {

        }
    }
}
