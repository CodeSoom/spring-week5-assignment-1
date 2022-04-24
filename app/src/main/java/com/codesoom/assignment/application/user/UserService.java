package com.codesoom.assignment.application.user;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.domain.user.UserRepository;
import com.codesoom.assignment.dto.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserData.RegisterUserRequest request){
        User user = request.toEntity();
        return userRepository.save(user);
    }

    public User updateUser(UserData.UpdateUserRequest request){
        User user = findUser(request.getId());
        user = user.change(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        return userRepository.save(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
