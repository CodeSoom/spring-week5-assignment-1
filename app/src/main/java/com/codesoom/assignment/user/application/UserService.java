package com.codesoom.assignment.user.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
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
  }


  /** 탈퇴할 회원을 id로 찾고, 그 회원정보요 삭제한다.
   *
   * @param id 탈퇴할 회원의 id
   */
  public void deleteUser(Long id) {
    User user = findUser(id);
    userRepository.delete(user);
  }

  /** 찾고자 하는 회원의 id로, 그 회원의 정보를 return
   *
   * @param id 찾고자 하는 회원의 id
   * @return 찾고자하는 회원
   */
  private User findUser(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }
}
