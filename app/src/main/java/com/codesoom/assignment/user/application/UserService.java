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

  /**
   * 신규회원의 정보를 저장하고, 그 회원을 return
   *
   * @param userData 새로 가입할 회원의 정보
   * @return 가입한 회원
   */
  public User createUser(UserData userData) {
    User user = mapper.map(userData, User.class);

    return userRepository.save(user);
  }}
