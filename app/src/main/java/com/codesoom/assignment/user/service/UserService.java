package com.codesoom.assignment.user.service;

import com.codesoom.assignment.common.exceptions.UserNotFoundException;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserCreateRequest;
import com.codesoom.assignment.user.dto.UserResponse;
import com.codesoom.assignment.user.dto.UserUpdateRequest;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원과 관련된 비즈니스 로직을 담당합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    /**
     * 모든 회원을 리턴합니다.
     */
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 리턴합니다.
     *
     * @param id 찾고자 하는 회원의 식별자
     * @return 찾은 회원
     */
    public UserResponse getUser(Long id) throws UserNotFoundException {
        User foundUser = findUser(id);

        return mapper.map(foundUser, UserResponse.class);
    }

    /**
     * 주어진 회원을 저장한 뒤, 저장된 회원을 리턴합니다.
     *
     * @param createRequest 저장하고자 하는 회원
     * @return 저장된 회원
     */
    public UserResponse createUser(UserCreateRequest createRequest) {
        User savedUser = userRepository.save(mapper.map(createRequest, User.class));

        return mapper.map(savedUser, UserResponse.class);
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 수정하고, 수정된 회원을 리턴합니다.
     *
     * @param id 수정하고자 하는 회원의 식별자
     * @param updateRequest 수정하고자 하는 회원
     * @return 수정된 회원
     * @throws UserNotFoundException 주어진 식별자에 해당하는 회원을 찾지 못했을 경우
     */
    public UserResponse updateUser(Long id, UserUpdateRequest updateRequest)
            throws UserNotFoundException {
        User user = findUser(id);
        User updatedUser = user.changeWith(mapper.map(updateRequest, User.class));

        return mapper.map(updatedUser, UserResponse.class);
    }

    /**
     * 주어진 id에 해당하는 회원을 찾아 삭제합니다.
     *
     * @param id 삭제하고자 하는 회원의 식별자
     * @throws UserNotFoundException 주어진 식별자에 해당하는 회원을 찾지 못했을 경우
     */
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = findUser(id);

        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 회원 id가 주어졌으므로 회원을 찾을 수 없습니다. 문제의 id = " + id));
    }

}
