package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserResponseDto;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import com.codesoom.assignment.user.dto.UserUpdateRequestDto;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 정보를 다룬다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::of)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUser(Long userId) {
        // TODO: 사용자 정보를 리턴한다.
        return null;
    }

    @Transactional
    public UserResponseDto updateUser(long userId, UserUpdateRequestDto requestDto) {
        // TODO: 갱신된 사용자 정보를 리턴한다.
        return null;
    }

    @Transactional
    public UserResponseDto createUser(UserSaveRequestDto requestDto) {
        User user = mapper.map(requestDto, User.class);
        User saved = userRepository.save(user);

        return UserResponseDto.of(saved);
    }

    public Long deleteUser(Long id) {
        // TODO: 사용자 정보를 삭제한다.
        return null;
    }
}
