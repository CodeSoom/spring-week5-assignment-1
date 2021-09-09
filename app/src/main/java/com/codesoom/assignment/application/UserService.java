package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(
            Mapper dozerMapper,
            UserRepository userRepository
    ){
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }


    /**
     * 사용자 정보를 받아 생성하고 리턴합니다.
     *
     * @param source 생성할 사용자 정보
     * @return 생성된 사용자 정보
     */
    public UserData createUser(UserData source) {

        User mappedSource = mapper.map(source, User.class);

        User createdUser = userRepository.save(mappedSource);

        return mapper.map(createdUser, UserData.class);
    }

}
