package com.codesoom.assignment.member.infrastructure;

import com.codesoom.assignment.member.domain.Member;
import com.codesoom.assignment.member.domain.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {
}
