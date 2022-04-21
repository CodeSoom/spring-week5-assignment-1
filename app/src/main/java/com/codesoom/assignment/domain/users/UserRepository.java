package com.codesoom.assignment.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 회원을 저장하고 리턴합니다.
     * @param saveRequest 회원 등록에 필요한 데이터
     * @return 회원 엔티티
     */
    default User save(UserSaveRequest saveRequest) {

        final User user = User.builder()
                .email(saveRequest.getSaveEmail())
                .name(saveRequest.getSaveName())
                .password(saveRequest.getSavePassword())
                .build();
        return save(user);
    }
}
