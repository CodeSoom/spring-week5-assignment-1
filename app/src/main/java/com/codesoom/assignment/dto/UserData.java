package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserData {
    public Long id;
    @NotBlank(message = "잘못된 요청입니다. 파라미터를 확인해 주세요.")
    public String name;
    @Email(message = "잘못된 요청입니다. 파라미터를 확인해 주세요.")
    public String email;
    @JsonIgnore
    @NotBlank(message = "잘못된 요청입니다. 파라미터를 확인해 주세요.")
    public String password;
}
