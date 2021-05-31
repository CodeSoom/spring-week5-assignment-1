package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public User get(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User create(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    public User patch(Long id, UserData userData) {
        User user = get(id);

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    public void delete(Long id) {
        User user = get(id);
        userRepository.delete(user);
    }
}
