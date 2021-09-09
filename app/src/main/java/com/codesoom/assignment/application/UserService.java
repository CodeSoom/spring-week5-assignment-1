package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

/**
 * 사용자 생성, 수정, 삭제 기능을 담당하는 클래스
 */
@Service
public interface UserService {

    /**
     * 사용자를 생성하고, 생성된 사용자 정보를 리턴합니다.
     * @param source 생성할 사용자 정보
     * @return 생성된 사용자를 반환한다.
     */
    User createUser(UserData source) throws Exception;

    /**
     * 사용자 정보를 수정하고 수정된 사용자 정보를 리턴합니다.
     * @param id 사용자 id
     * @param source 수정할 사용자 정보
     * @return 수정된 사용자를 반환한다.
     */
    User updateUser(Long id, UserData source);

    /**
     * 사용자를 삭제합니다.
     * @param id 삭제할 사용자 id
     */
    void deleteUser(Long id);

    /**
     * 이메일 중복확인
     * @oaram 중복 확인용 메일
     * @return 중복있음 - true / 중복없음 - false
     * @throws Exception 중복이 있으면 UserEmailDuplicateException 예외를 던진다.
     */
    boolean emailCheck(String mail) throws Exception;

}

