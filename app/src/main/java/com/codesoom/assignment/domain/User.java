package com.codesoom.assignment.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
// 라이브러리 사용을 위해 Setter 를 무작정 공개하는 것은 좋지 않은 전략이기 때문에 엑세스 레벨을 Private 으로 둔다.
// 라이브러리는 reflection API 로 동작하기 때문에, 접근 권한이 Public 일 필요가 없다.
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String email;
    String password;
}
