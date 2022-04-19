package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.domain.users.UserRepository;
import com.codesoom.assignment.domain.users.UserSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 조회, 변경 담당
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 저장 후 리턴합니다.
     * @param saveRequest 회원 등록에 필요한 데이터
     */
    public User save(final UserSaveRequest saveRequest) {

        final User user = User.builder()
                .email(saveRequest.getEmail())
                .name(saveRequest.getName())
                .password(saveRequest.getPassword())
                .build();

        return userRepository.save(user);
    }
}
