package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountData {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("password")
    private String password;

    @NotBlank
    @Mapping("email")
    private String email;

    public Account changeData(AccountData source) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return mapper.map(source, Account.class);
    }
}
