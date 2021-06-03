package com.codesoom.assignment.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    /**
     * 사용자 데이터를 수정한다.
     *
     * @param source 수정된 사용자 데이터
     */
    public void update(User source) {
        this.name = source.name;
        this.email = source.email;
        this.password = source.password;
    }
}
