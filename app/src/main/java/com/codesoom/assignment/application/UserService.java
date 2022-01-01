//TODO : 1. 회원 생성하기 - Post /user
//TODO : 2. 회원 수정하기 - Post /user/{id}
//TODO : 3. 회원 삭제하기 - /Delete /user/{id}

package com.codesoom.assignment.application;

import com.codesoom.assignment.UserEmailDuplicationException;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRegisterationData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(Mapper dozerMapper,UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public User registerUser(UserRegisterationData registerationData) {
        String email = registerationData.getEmail();

        if(userRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationException(email);
        }

        User user = mapper.map(registerationData, User.class);
        return userRepository.save(user);
    }
}
