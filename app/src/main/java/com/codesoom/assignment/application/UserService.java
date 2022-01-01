package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;

import java.util.List;

/**
 * 유저를 관리합니다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, Mapper dozerMapper) {
        this.userRepository = userRepository;
        this.mapper = dozerMapper;
    }

    /**
     * 유저 목록을 반환합니다.
     *
     * @return 유저 목록
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 주어진 id와 일치하는 유저를 반환합니다.
     *
     * @param id 유저 id
     * @return 주어진 id와 일치하는 유저
     * @throws UserNotFoundException '유저를 찾지 못했다'는 예외
     */
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(
                        String.format("유저 id: %d를 찾을 수 없어, 조회할 수 없습니다.", id)));
    }

    /**
     * 주어진 유저와 동일한 유저를 생성하고 반환합니다.
     *
     * @param userData 생성할 유저
     * @return 생성한 유저
     */
    public User createUser(UserData userData) {
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    /**
     * 주어진 id와 일치하는 유저를 주어진 source 유저와 동일하도록 변경합니다.
     *
     * @param id     유저 id
     * @param source 변경할 유저의 source
     * @return 변경한 유저
     * @throws UserNotFoundException '유저를 찾지 못했다'는 예외
     */
    public User updateUser(Long id, UserData source) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(
                        String.format("유저 id: %d를 찾을 수 없어, 업데이트할 수 없습니다.", id)));

        User updatedUser = User.builder()
                .id(user.getId())
                .name(source.getName())
                .email(source.getEmail())
                .password(source.getPassword())
                .build();

        return userRepository.save(updatedUser);
    }

    /**
     * 주어진 id와 일치하는 유저를 삭제합니다.
     *
     * @param id 유저 id
     * @throws UserNotFoundException '유저를 찾지 못했다'는 예외
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(
                        String.format("유저 id: %d를 찾을 수 없어, 삭제할 수 없습니다.", id)));

        userRepository.delete(user);
    }
}
