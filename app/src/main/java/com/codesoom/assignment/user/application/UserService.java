package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자 정보를 다룬다.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    public List<UserResponseDto> getUsers() {
        // TODO: 사용자의 목록을 리턴한다.
        return null;
    }

    public UserResponseDto getUser(Long userId) {
        // TODO: 사용자 정보를 리턴한다.
        return null;
    }
}
