package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저를 관리합니다.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    /**
     * 유저를 저장하고 저장된 유저를 리턴한다.
     *
     * @param source 저장할 유저
     * @return 저장된 유저
     */
    public User createUser(UserData source) {
        User user = mapper.map(source, User.class);

        return userRepository.save(user);
    }

    /**
     * 회원을 수정하고 수정된 회원을 리턴한다.
     *
     * @param id 수정할 회원의 아이디
     * @param source 수정할 회원
     * @return 수정된 회원
     * @throws UserNotFoundException 회원을 찾지 못한 경우
     */
    public User updateUser(Long id, UserData source) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.change(
                source.getName(),
                source.getEmail(),
                source.getPassword()
        );

        return user;
    }

    /**
     * 회원을 삭제하고 삭제한 회원을 리턴한다.
     * @param id 삭제할 회원의 아이디
     * @return 삭제된 회원
     * @throws UserNotFoundException 회원을 찾지 못한 경우
     */
    public User deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);

        return user;
    }
}
