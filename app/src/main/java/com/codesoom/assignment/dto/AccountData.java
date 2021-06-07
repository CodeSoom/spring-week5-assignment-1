package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 회원정보 DTO입니다.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountData {
    /**
     * 회원정보 고유 ID값 입니다.
     */
    private Long id;

    /**
     * 회원의 이름입니다.
     */
    @NotBlank
    @Mapping("name")
    private String name;

    /**
     * 회원의 비밀번호입니다.
     */
    @NotBlank
    @Mapping("password")
    private String password;

    /**
     * 회원의 이메일입니다.
     */
    @NotBlank
    @Mapping("email")
    private String email;

    public Account changeData(AccountData source) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return mapper.map(source, Account.class);
    }
}
