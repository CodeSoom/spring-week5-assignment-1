package com.codesoom.assignment.user.adapter.out.persistence;

import com.codesoom.assignment.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeInMemoryUserPersistenceAdapter {
    private static final  AtomicLong id = new AtomicLong(1L);
    private final List<User> users = new ArrayList<>();

    public User save(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        User user = User.builder()
                .id(id.getAndIncrement())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();

        users.add(user);

        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public void delete(User entity) {
        users.remove(entity);
    }
}
