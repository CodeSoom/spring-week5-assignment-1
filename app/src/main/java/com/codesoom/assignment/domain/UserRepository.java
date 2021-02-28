package com.codesoom.assignment.domain;

public interface UserRepository {
    User save(User user);

    void delete(User user);

    public class Fake implements UserRepository {
        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public void delete(User user) {

        }
    }
}
