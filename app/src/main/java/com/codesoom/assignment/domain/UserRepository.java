package com.codesoom.assignment.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

    public class Fake implements UserRepository {
        private final List<User> users = new ArrayList<>();

        @Override
        public Optional<User> findById(Long id) {
            if (users.size() < id) {
                return Optional.empty();
            }
            return Optional.of(users.get(id.intValue() - 1));
        }

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
        public void deleteById(Long id) {
            users.remove(id.intValue() - 1);
        }
    }
}
