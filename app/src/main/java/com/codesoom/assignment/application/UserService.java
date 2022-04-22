package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.domain.users.UserRepository;
import com.codesoom.assignment.domain.users.UserSaveRequest;
import com.codesoom.assignment.domain.users.UserUpdateRequest;
import com.codesoom.assignment.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 조회, 변경 담당
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 저장 후 리턴합니다.
     *
     * @param saveRequest 회원 등록에 필요한 데이터
     * @return 저장된 회원
     */
    public User saveUser(final UserSaveRequest saveRequest) {
        return userRepository.save(saveRequest);
    }

    /**
     * 단일 회원을 리턴합니다.
     *
     * @param userId 회원 아이디
     * @return 회원
     * @throws UserNotFoundException 회원을 찾지 못한 경우
     */
    @Transactional(readOnly = true)
    public User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * 회원을 수정하고 리턴합니다.
     *
     * @param user          수정 대상 회원
     * @param updateRequest 수정할 회원 데이터
     * @return 수정된 회원
     */
    public User updateUser(final User user, final UserUpdateRequest updateRequest) {
        return user.update(updateRequest);
    }

    /**
     * 회원을 삭제합니다.
     * @param user 삭제할 회원
     */
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }
}
