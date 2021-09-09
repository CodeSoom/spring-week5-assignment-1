package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;

/**
 * 회원 관리 담당.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원을 생성해 리턴합니다.
     *
     * @param user 생성할 회원
     * @return 생성된 회원
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 회원 정보를 수정 후 리턴합니다.
     *
     * @param id     찾을 회원 식별자
     * @param source 바꿀 회원 정보
     * @return 수정된 회원
     */
    public User updateUser(Long id, User source) {
        return userRepository.findById(id)
            .map(user -> {
                user.changeInfo(source);

                return userRepository.save(user);
            }).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 식별자로 회원을 찾아 삭제합니다.
     *
     * @param id 식별자
     */
    public void deleteUser(Long id) {

    }
}
