package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.CreateUserData;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.dto.UpdateUserData;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public User createUser(CreateUserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UpdateUserData userData) {
        User user = findUser(id);

        user.change(userData.getName(), userData.getEmail(), userData.getPassword());

        return user;
    }

    public User deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
