package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public User createUser(UserData userData) {
        mapper = DozerBeanMapperBuilder.buildDefault();
        User newUser = mapper.map(userData, User.class);

        return userRepository.save(newUser);
    }

    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);
        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    public User deleteUser(Long id){
        User user = findUser(id);
        userRepository.delete(user);

        return user;
    }

    private User findUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
