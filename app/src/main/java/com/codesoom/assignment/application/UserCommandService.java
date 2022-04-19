package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 저장, 변경을 담당합니다.
 */
@Transactional
@Service
public class UserCommandService implements UserSaveService{

    private final UserRepository repository;

    public UserCommandService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(UserSaveRequest userSaveRequest) {
        return repository.save(userSaveRequest);
    }

}
