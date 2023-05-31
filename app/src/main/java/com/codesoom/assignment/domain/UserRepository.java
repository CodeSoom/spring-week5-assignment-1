package com.codesoom.assignment.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	@Override
	public List<User> findAll();

}
