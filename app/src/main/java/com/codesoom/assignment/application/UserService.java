package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDto;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 비지니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final Mapper mapper;

    private final UserRepository userRepository;

    /**
     * 회원을 저장합니다.
     * @param userDto 저장할 회원의 정보
     * @return 저장된 회원의 정보
     */
    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(mapper.map(userDto, User.class));
        return mapper.map(savedUser,UserDto.class);
    }
}
