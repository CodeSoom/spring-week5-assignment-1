package com.codesoom.assignment.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.InvalidParameterException;

@Entity
@Getter
@ToString(of = {"id", "name", "password", "email"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String password;
    private String email;

    @Builder
    public Member(Long id, String name, String password, String email) {
        if (StringUtils.isEmpty(name)) throw new IllegalArgumentException("이름이 비어있습니다.");
        if (StringUtils.isEmpty(email)) throw new IllegalArgumentException("이메일이 비어있습니다.");
        if (StringUtils.isEmpty(password)) throw new IllegalArgumentException("비밀번호가 비어있습니다.");

        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void modifyMemberInfo(Member member) {
        this.name = member.getName();
        this.password = member.getPassword();
        this.email = member.getEmail();
    }


}
