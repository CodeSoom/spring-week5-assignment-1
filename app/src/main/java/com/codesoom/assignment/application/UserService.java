package com.codesoom.assignment.application;

import javax.transaction.Transactional;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;

import org.springframework.stereotype.Service;

/**
 * User 리소스
 * 수행할 작업을 정의하고, Domain layer에 수행을 위임한다.
 *
 * @see User
 * @see UserRepository
 */
@Service
@Transactional
public class UserService {
    private final Mapper dozerMapper;
    private final UserRepository userRepository;

    public UserService(
        final Mapper dozerMapper, final UserRepository userRepository
    ) {
        this.dozerMapper = dozerMapper;
        this.userRepository = userRepository;
    }

    /**
     * User를 생성하고 리턴한다.
     *
     * @param userData User 생성에 필요한 데이터
     * @return 생성한 User
     */
    public User createUser(final UserData userData) {
        User user = dozerMapper.map(userData, User.class);
        return userRepository.save(user);
    }

}
