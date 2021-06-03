package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 사용자.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    /**
     * 사용자 정보를 사용자로 수정합니다.
     * @param mapper 객체를 맵핑하는 매퍼
     * @param userData 수정할 사용자 정보
     */
    public void modify(Mapper mapper, UserData userData) {
        mapper.map(userData, this);
    }
}
