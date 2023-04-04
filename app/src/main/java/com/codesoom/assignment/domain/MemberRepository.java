package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findById(Long id);

    Member save(Member member);

    List<Member> findAll();

}
