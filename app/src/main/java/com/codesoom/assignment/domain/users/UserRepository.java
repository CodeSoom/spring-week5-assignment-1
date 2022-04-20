package com.codesoom.assignment.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 회원을 저장하고 리턴합니다.
     * @param saveRequest 회원 등록에 필요한 데이터
     */
    default User save(UserSaveRequest saveRequest) {

        final User user = User.builder()
                .email(saveRequest.getEmail())
                .name(saveRequest.getName())
                .password(saveRequest.getPassword())
                .build();
        return save(user);
    }
}
