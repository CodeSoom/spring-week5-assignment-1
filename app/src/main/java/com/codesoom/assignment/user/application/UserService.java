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

    /**
     * 등록된 모든 사용자 목록을 가져온다.
     */
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 등록된 사용자 id를 가진 사용자를 리턴한다.
     * @param userId 등록된 사용자 id
     * @return 등록된 사용자
     */
    public UserResponseDto getUser(Long userId) throws UserNotFoundException {
        final User user = findUser(userId);

        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto updateUser(long userId, UserUpdateRequestDto requestDto) {
        // TODO: 갱신된 사용자 정보를 리턴한다.
        return null;
    }

    /**
     * 사용자를 등록하고, 등록된 정보를 리턴한다.
     * @param requestDto 등록할 사용자 정보
     * @return 등록된 사용자 정보
     */
    @Transactional
    public UserResponseDto createUser(UserSaveRequestDto requestDto) {
        User user = mapper.map(requestDto, User.class);
        User saved = userRepository.save(user);

        return UserResponseDto.of(saved);
    }

    /**
     * 등록된 사용자를 삭제한다.
     * @param id 등록된 사용자 id
     * @return 삭제된 사용자 id
     */
    public Long deleteUser(Long id) {
        // TODO: 사용자 정보를 삭제한다.
        return null;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
