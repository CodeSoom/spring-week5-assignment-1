package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong newId = new AtomicLong(0L);

    @Override
    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> {
                    return user.getId().equals(id);
                })
                .findFirst();
    }

    @Override
    public User save(User user) {
        final User newUser = new User(newId.incrementAndGet(), user.getName(), user.getEmail(), user.getPassword());
        users.add(newUser);
        return newUser;
    }

    @Override
    public void delete(User user) {
        users.removeIf(element -> {
            return element.getId().equals(user.getId());
        });
    }
}
