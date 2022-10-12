package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "이름은 필수값 입니다.")
    private String name;

    @NotBlank(message = "제조사는 필수값 입니다.")
    private String maker;

    @NotNull(message = "가격은 필수값 입니다.")
    private Integer price;

    private String imageUrl;
}
