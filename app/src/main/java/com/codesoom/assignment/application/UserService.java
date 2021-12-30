//TODO : 1. 회원 생성하기 - Post /user
//TODO : 2. 회원 수정하기 - Post /user/{id}
//TODO : 3. 회원 삭제하기 - /Delete /user/{id}

package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    public User createUser(User user) {
        return null;
    }

    public User updateUser(Long id, User user) {
        return null;
    }


}
