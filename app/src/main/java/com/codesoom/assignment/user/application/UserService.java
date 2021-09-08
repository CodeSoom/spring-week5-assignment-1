package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

  private final Mapper mapper;
  private final UserRepository userRepository;

  public UserService(
      Mapper dozerMapper,
      UserRepository userRepository
  ) {
    this.mapper = dozerMapper;
    this.userRepository = userRepository;
  }


  public User createUser(UserData userData) {
    User user = mapper.map(userData, User.class);

    return userRepository.save(user);
  }}
