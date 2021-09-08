package com.codesoom.assignment.user.infra;

import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository  extends UserRepository, CrudRepository<User, Long> {
  List<User> findAll();

  Optional<User> findById(Long id);

  User save(User user);

  void delete(User user);

}
