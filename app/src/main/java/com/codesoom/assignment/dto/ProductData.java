package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @NotBlank(message = "이름은 필수값입니다.")
    private String maker;

    @NotNull(message = "가격은 필수값입니다.")
    private Integer price;

    private String imageUrl;

    public ProductData() {}
}
