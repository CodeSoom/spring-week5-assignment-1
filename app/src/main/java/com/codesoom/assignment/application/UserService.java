package com.codesoom.assignment.application;

import com.codesoom.assignment.exception.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequestDto;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.dto.UserUpdateRequestDto;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 비지니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final Mapper mapper;

    private final UserRepository userRepository;

    /**
     * 회원을 저장합니다.
     *
     * @param userDto 저장할 회원의 정보
     * @return 저장된 회원의 정보
     */
    @Transactional
    public UserDto createUser(UserCreateRequestDto userDto) {
        User savedUser = userRepository.save(mapper.map(userDto, User.class));
        return mapper.map(savedUser, UserDto.class);
    }

    /**
     * id에 해당하는 회원을 수정합니다
     *
     * @param id                   수정할 회원의 id
     * @param userUpdateRequestDto 수정할 회원의 정보
     * @return 수정된 회원의 정보
     * @throws UserNotFoundException id에 해당하는 회원이 없는경우
     */
    @Transactional
    public UserDto updateUser(long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user = findUser(id);
        user.change(mapper.map(userUpdateRequestDto, User.class));
        return mapper.map(user, UserDto.class);
    }

    /**
     * id에 해당하는 회원을 삭제합니다.
     *
     * @param id 삭제할 회원의 id
     */
    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    /**
     * id에 해당하는 회원을 반환합니다.
     *
     * @param id 찾을 회원의 id
     * @return id에 해당하는 회원
     */
    private User findUser(long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
    }
}
