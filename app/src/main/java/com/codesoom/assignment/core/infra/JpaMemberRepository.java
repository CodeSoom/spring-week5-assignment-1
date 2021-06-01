package com.codesoom.assignment.core.infra;

import com.codesoom.assignment.core.domain.Member;
import com.codesoom.assignment.core.domain.MemberRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaMemberRepository
        extends MemberRepository, CrudRepository<Member, Long> {
    Optional<Member> findById(Long id);

    Member save(Member member);

    void delete(Member member);
}
