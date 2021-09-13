package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.domain.UserRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, Mapper dozerMapper) {
        this.userRepository = userRepository;
        this.mapper = dozerMapper;
    }

    public UserData createUser(UserData userData) {
        User user = mapper.map(userData, User.class);
        return new UserData(userRepository.save(user));
    }

    public UserData selectUser(Long id) {
        return new UserData(getUser(id));
    }

    public List<UserData> selectUsers() {
        return UserData.ofList(userRepository.findAll());
    }

    public UserData modifyUser(Long id, UserData userData) {
        User user = getUser(id);
        user.modifyUser(userData);
        return new UserData(userRepository.save(user));
    }

    public UserData deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
        return new UserData(user);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
