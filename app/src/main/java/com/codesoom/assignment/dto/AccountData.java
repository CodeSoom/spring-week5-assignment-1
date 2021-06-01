package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountData {
    private Long acc_uid;

    @NotBlank
    @Mapping("acc_id")
    private String acc_id;

    @NotBlank
    @Mapping("acc_password")
    private String acc_password;

    public Account changeData(Account acc, AccountData source) {
        
        return acc;
    }
}
