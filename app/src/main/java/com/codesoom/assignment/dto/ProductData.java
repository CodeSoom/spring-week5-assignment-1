package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class ProductData {

    @NotBlank(message = "이름은 필수값입니다.")
    @Size(min = 2, max = 20, message = "이름의 길이가 범위를 벗어납니다.")
    private String name;

    @NotBlank(message = "메이커는 필수값입니다.")
    @Size(max = 10, message = "메이커 길이가 범위를 벗어납니다.")
    private String maker;

    @NotNull(message = "가격은 필수값입니다.")
    @Min(value = 0, message = "가격은 음수일 수 없습니다.")
    @Max(value = 10000000, message = "가격의 한계치를 벗어났습니다.")
    private Integer price;

    private String imageUrl;

    public ProductData() {}
}
