package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository {
    User save(User user);

    User update(User user);

    void delete(User user);
}
